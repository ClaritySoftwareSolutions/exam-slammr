package uk.co.claritysoftware.exam.slammr.webapp.web.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * MockMvc test class for {@link LoginPageController}
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource({"classpath:test-context.properties"})
public class LoginPageControllerMockMvcTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void shouldGetLoginPageGivenUnauthenticatedUser() throws Exception {
        // Given
        RequestBuilder request = get("/login");

        // When
        MockHttpServletResponse response = mvc.perform(request)
                .andReturn().getResponse();

        // Then
        assertThat(response.getStatus())
                .as("HTTP Status code is 200")
                .isEqualTo(200);
    }

    @Test
    public void shouldNotGetLoginPageGivenAuthenticatedUser() throws Exception {
        // Given
        RequestBuilder request = get("/login")
                .with(user("aUser"));

        // When
        MockHttpServletResponse response = mvc.perform(request)
                .andReturn().getResponse();

        // Then
        assertThat(response.getStatus())
                .as("HTTP Status code is 302")
                .isEqualTo(302);
        assertThat(response.getHeader("Location"))
                .as("Location header is root")
                .isEqualTo("/");
    }
}