package uk.co.claritysoftware.exam.slammr.user.rest.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static uk.co.claritysoftware.exam.slammr.user.rest.testsupport.assertj.MockHttpServletResponseAssert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 * {@link MockMvc} test class for {@link UserController}
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

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