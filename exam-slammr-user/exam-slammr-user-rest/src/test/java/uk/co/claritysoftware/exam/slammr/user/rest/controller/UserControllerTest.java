package uk.co.claritysoftware.exam.slammr.user.rest.controller;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.junit.Ignore;
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
import uk.co.claritysoftware.exam.slammr.user.rest.model.UserProfile;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static uk.co.claritysoftware.exam.slammr.user.rest.testsupport.assertj.MockHttpServletResponseAssert.assertThat;
import static uk.co.claritysoftware.exam.slammr.user.rest.testsupport.model.UserProfileTestDataFactory.aValidUserProfile;

/**
 * {@link MockMvc} test class for {@link UserController}
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DynamoDBMapper dynamoDBMapper;

    @Test
    public void shouldGetUserProfile() throws Exception {
        // Given
        String identityId = "12345";
        UserProfile expectedUserProfile = aValidUserProfile().build();
        given(dynamoDBMapper.load(UserProfile.class, identityId))
                .willReturn(expectedUserProfile);

        // When
        MockHttpServletResponse response = mockMvc.perform(get(UserController.BASE_PATH)
                .header("x-exam-slammr-identityId", identityId))
                .andReturn().getResponse();

        // Then
        assertThat(response)
                .as("Correct UserProfile returned with HTTP 200")
                .hasStatusCode(HttpStatus.OK)
                .hasBody(expectedUserProfile);
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

    @Ignore
    @Test
    public void shouldRegisterNewUser() throws Exception {
        // Given
        String requestBody = "" +
                "{" +
                "   \"socialIdentityProvider\":\"facebook\"," +
                "   \"userProfile\":{" +
                "       \"name\":\"A person\"," +
                "       \"email\":\"a.person@email.com\"," +
                "       \"profilePicture\":{" +
                "           \"height\":50," +
                "           \"url\":\"https://picture.url.com\"," +
                "           \"width\":50" +
                "       }," +
                "       \"id\":\"1234567890\"" +
                "   }" +
                "}";

        // When
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.post(UserController.BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-amz-cognitoIdentityId", "some-id")
                .content(requestBody))
                .andReturn().getResponse();

        // Then
        // TODO - make some assertions when the implementation has been written
        assertThat(response).hasStatusCode(HttpStatus.CREATED);
    }

    @Ignore
    @Test
    public void shouldFailToRegisterNewUserGivenNoHeader() throws Exception {
        // Given
        String requestBody = "" +
                "{" +
                "   \"socialIdentityProvider\":\"facebook\"," +
                "   \"userProfile\":{" +
                "       \"name\":\"A person\"," +
                "       \"email\":\"a.person@email.com\"," +
                "       \"profilePicture\":{" +
                "           \"height\":50," +
                "           \"url\":\"https://picture.url.com\"," +
                "           \"width\":50" +
                "       }," +
                "       \"id\":\"1234567890\"" +
                "   }" +
                "}";

        // When
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.post(UserController.BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andReturn().getResponse();

        // Then
        assertThat(response).hasStatusCode(HttpStatus.NOT_FOUND);
    }

}