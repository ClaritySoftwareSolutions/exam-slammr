package uk.co.claritysoftware.exam.slammr.webapp.web.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
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

/**
 * MockMvc test class for {@link HomePageController}
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource({"classpath:test-context.properties"})
public class HomePageControllerMockMvcTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void shouldGetHomepageGivenUnauthenticatedUser() throws Exception {
        // Given
        RequestBuilder request = get("/");

        // When
        MockHttpServletResponse response = mvc.perform(request)
                .andReturn().getResponse();

        // Then
        String responseBody = response.getContentAsString();
        Document htmlDocument = Jsoup.parse(responseBody);

        assertThat(response.getStatus())
                .as("HTTP Status code is 200")
                .isEqualTo(200);
        assertThat(htmlDocument.selectFirst("#top-bar .user-profile-actions"))
                .as("Top bar does not contain user profile actions")
                .isNull();
        assertThat(htmlDocument.selectFirst("#top-bar li.ctas a[href=/login]"))
                .as("Top bar contains a login link")
                .isNotNull();
        assertThat(htmlDocument.selectFirst("#top-bar li.ctas a[href=/register]"))
                .as("Top bar contains a register link")
                .isNotNull();
        assertThat(htmlDocument.selectFirst(".home.hero.register"))
                .as("There is a hero section for registration")
                .isNotNull();
    }

    @Test
    public void shouldGetHomepageGivenAuthenticatedUser() throws Exception {
        // Given
        RequestBuilder request = get("/")
                .with(user("some-user"));

        // When
        MockHttpServletResponse response = mvc.perform(request)
                .andReturn().getResponse();

        // Then
        String responseBody = response.getContentAsString();
        Document htmlDocument = Jsoup.parse(responseBody);

        assertThat(response.getStatus())
                .as("HTTP Status code is 200")
                .isEqualTo(200);
        assertThat(htmlDocument.selectFirst("#top-bar .user-profile-actions"))
                .as("Top bar contains user profile actions")
                .isNotNull();
        assertThat(htmlDocument.selectFirst("#top-bar li.ctas a[href=/login]"))
                .as("Top bar does not contain a login link")
                .isNull();
        assertThat(htmlDocument.selectFirst("#top-bar li.ctas a[href=/register]"))
                .as("Top bar does not contain a register link")
                .isNull();
        assertThat(htmlDocument.selectFirst(".home.hero.register"))
                .as("There is not a hero section for registration")
                .isNull();
    }

}