package uk.co.claritysoftware.exam.slammr.user.rest.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static uk.co.claritysoftware.exam.slammr.testsupport.assertj.MockHttpServletResponseAssert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import uk.co.claritysoftware.exam.slammr.user.rest.model.UserProfile;
import uk.co.claritysoftware.exam.slammr.user.service.dynamodb.UserProfileItem;
import uk.co.claritysoftware.exam.slammr.user.testsupport.rest.model.UserProfileTestDataFactory;
import uk.co.claritysoftware.exam.slammr.user.testsupport.service.dynamodb.UserProfileItemTestDataFactory;

/**
 * {@link MockMvc} test class for {@link UserController}
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DynamoDBMapper dynamoDBMapper;

    @Test
    public void shouldGetUserProfile() throws Exception {
        // Given
        String identityId = "12345";
        UserProfileItem expectedUserProfileItem = UserProfileItemTestDataFactory.mrBurnsUserProfileItem().build();
        given(dynamoDBMapper.load(UserProfileItem.class, identityId))
                .willReturn(expectedUserProfileItem);

        UserProfile expectedUserProfile = UserProfileTestDataFactory.mrBurnsUserProfile().build();

        // When
        MockHttpServletResponse response = mockMvc.perform(get(UserController.BASE_PATH)
                .header("x-exam-slammr-identityId", identityId))
                .andReturn().getResponse();

        // Then
        assertThat(response)
                .as("Correct UserProfile returned with HTTP 200")
                .hasStatusCode(HttpStatus.OK)
                .hasBody(expectedUserProfile, objectMapper);
    }

    @Test
    public void shouldNotGetUserProfileGivenNonExistentId() throws Exception {
        // Given
        String identityId = "12345";
        given(dynamoDBMapper.load(UserProfile.class, identityId))
                .willReturn(null);

        // When
        MockHttpServletResponse response = mockMvc.perform(get(UserController.BASE_PATH)
                .header("x-exam-slammr-identityId", identityId))
                .andReturn().getResponse();

        // Then
        assertThat(response)
                .as("No UserProfile returned with HTTP 404")
                .hasStatusCode(HttpStatus.NOT_FOUND)
                .hasNoBody();
    }

    @Test
    public void shouldFailToGetUserProfileGivenNoHeader() throws Exception {
        // Given

        // When
        MockHttpServletResponse response = mockMvc.perform(get(UserController.BASE_PATH))
                .andReturn().getResponse();

        // Then
        assertThat(response)
                .as("No UserProfile returned with HTTP 401")
                .hasStatusCode(HttpStatus.UNAUTHORIZED)
                .hasNoBody();
    }

    @Test
    public void shouldRegisterNewUser() throws Exception {
        // Given
        String identityId = "12345";
        String requestBody = "" +
                "{" +
                "   \"firstname\":\"Waylon\"," +
                "   \"surname\":\"Smithers\"," +
                "   \"nickname\":\"Smithers\"," +
                "   \"email\":\"waylon.smithers@springfield.com\"," +
                "   \"profilePictureUrl\":\"http://profile.pics/waylon.smithers\"" +
                "}";

        // When
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.post(UserController.BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-exam-slammr-identityId", identityId)
                .content(requestBody))
                .andReturn().getResponse();

        // Then
        assertThat(response).hasStatusCode(HttpStatus.CREATED);
    }

    @Test
    public void shouldFailToRegisterNewUserGivenNoHeader() throws Exception {
        // Given
        String requestBody = "" +
                "{" +
                "   \"firstname\":\"Waylon\"," +
                "   \"surname\":\"Smithers\"," +
                "   \"nickname\":\"Smithers\"," +
                "   \"email\":\"waylon.smithers@springfield.com\"," +
                "   \"profilePictureUrl\":\"http://profile.pics/waylon.smithers\"" +
                "}";

        // When
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.post(UserController.BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andReturn().getResponse();

        // Then
        assertThat(response).hasStatusCode(HttpStatus.UNAUTHORIZED);
    }

}