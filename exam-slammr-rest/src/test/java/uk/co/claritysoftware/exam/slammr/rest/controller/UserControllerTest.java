package uk.co.claritysoftware.exam.slammr.rest.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static uk.co.claritysoftware.exam.slammr.rest.controller.UserController.BASE_PATH;
import static uk.co.claritysoftware.exam.slammr.rest.testsupport.assertj.MockHttpServletResponseAssert.assertThat;

/**
 * {@link MockMvc} test class for {@link UserController}
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
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
        MockHttpServletResponse response = mockMvc.perform(post(BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andReturn().getResponse();

        // Then
        // TODO - make some assertions when the implementation has been written
        assertThat(response).hasStatusCode(HttpStatus.CREATED);
    }

    @Test
    public void shouldFailToRegisterNewUserGivenNullPrincipal() throws Exception {
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
        MockHttpServletResponse response = mockMvc.perform(post(BASE_PATH)
                .with(anonymous())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andReturn().getResponse();

        // Then
        assertThat(response).hasStatusCode(HttpStatus.FORBIDDEN);
    }

}