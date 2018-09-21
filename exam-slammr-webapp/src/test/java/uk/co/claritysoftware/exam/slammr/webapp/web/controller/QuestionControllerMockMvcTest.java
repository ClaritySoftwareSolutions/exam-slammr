package uk.co.claritysoftware.exam.slammr.webapp.web.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

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
 * MockMvc test class for {@link QuestionController}
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource({"classpath:test-context.properties"})
public class QuestionControllerMockMvcTest {

	@Autowired
	private MockMvc mvc;

	@Test
	public void shouldNotCreateNewQuestionGivenUnauthenticatedUser() throws Exception {
		// Given
		RequestBuilder request = post("/question")
				.with(csrf());

		// When
		MockHttpServletResponse response = mvc.perform(request)
				.andReturn().getResponse();

		// Then
		assertThat(response.getStatus())
				.as("HTTP Status code is 200")
				.isEqualTo(200);
	}


}