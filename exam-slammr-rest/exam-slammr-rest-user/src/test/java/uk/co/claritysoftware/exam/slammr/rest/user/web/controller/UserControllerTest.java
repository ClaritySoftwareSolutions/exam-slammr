package uk.co.claritysoftware.exam.slammr.rest.user.web.controller;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import uk.co.claritysoftware.exam.slammr.rest.user.service.dynamodb.UserProfileItem;
import uk.co.claritysoftware.exam.slammr.rest.user.web.model.UserProfile;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static uk.co.claritysoftware.exam.slammr.rest.user.testsupport.service.dynamodb.UserProfileItemTestDataFactory.mrBurnsUserProfileItem;
import static uk.co.claritysoftware.exam.slammr.rest.user.testsupport.web.model.UserProfileTestDataFactory.mrBurnsUserProfile;
import static uk.co.claritysoftware.exam.slammr.web.testsupport.assertj.MockHttpServletResponseAssert.assertThat;

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
        UserProfileItem expectedUserProfileItem = mrBurnsUserProfileItem().build();
        given(dynamoDBMapper.load(UserProfileItem.class, identityId))
                .willReturn(expectedUserProfileItem);

        UserProfile expectedUserProfile = mrBurnsUserProfile().build();

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
        assertThat(response)
                .as("No body returned with HTTP 201")
                .hasStatusCode(HttpStatus.CREATED)
                .hasNoBody();
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
        assertThat(response)
                .as("No body returned with HTTP 401")
                .hasStatusCode(HttpStatus.UNAUTHORIZED)
                .hasNoBody();
    }

    @Test
    public void shouldFailToRegisterNewUserGivenInvalidRequestBody() throws Exception {
        // Given
        String identityId = "12345";
        String requestBody = "" +
                "{" +
                "   \"firstname\":\"Waylon\"," +
                "   \"surname\":null," +
                "   \"nickname\":\"Smithers\"," +
                "   \"email\":\"not a valid email address\"," +
                "   \"profilePictureUrl\":\"http://profile.pics/waylon.smithers\"" +
                "}";

        // When
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.post(UserController.BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-exam-slammr-identityId", identityId)
                .content(requestBody))
                .andReturn().getResponse();

        // Then
        assertThat(response)
                .as("No body returned with HTTP 400")
                .hasStatusCode(HttpStatus.BAD_REQUEST)
                .hasNoBody();
    }
}