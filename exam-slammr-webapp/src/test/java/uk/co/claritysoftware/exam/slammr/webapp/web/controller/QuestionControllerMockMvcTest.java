package uk.co.claritysoftware.exam.slammr.webapp.web.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static uk.co.claritysoftware.exam.slammr.webapp.testsupport.web.model.question.CreateQuestionTestDataFactory.aSimpleCreateQuestionAboutTriangles;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import uk.co.claritysoftware.exam.slammr.webapp.web.model.question.CreateQuestion;

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
	public void shouldNotGetQuestionPageGivenUnauthenticatedUser() throws Exception {
		// Given
		RequestBuilder request = get("/question/new");

		// When
		MockHttpServletResponse response = mvc.perform(request)
				.andReturn().getResponse();

		// Then
		assertThat(response.getStatus())
				.as("HTTP Status code is 403")
				.isEqualTo(403);
	}

	@Test
	public void shouldGetQuestionPageGivenAuthenticatedUser() throws Exception {
		// Given
		RequestBuilder request = get("/question/new")
				.with(user("aUser"));

		// When
		MockHttpServletResponse response = mvc.perform(request)
				.andReturn().getResponse();

		// Then
		assertThat(response.getStatus())
				.as("HTTP Status code is 200")
				.isEqualTo(200);
	}

	@Test
	public void shouldNotCreateNewQuestionGivenUnauthenticatedUser() throws Exception {
		// Given
		CreateQuestion question = aSimpleCreateQuestionAboutTriangles().build();

/*
		RequestBuilder request = post("/question")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.with(csrf());
		addFormFieldsToRequest((MockHttpServletRequestBuilder)request, question);
*/
		RequestBuilder request = MockMvcRequestBuilderUtils.postForm("/question", question)
				.with(csrf());

		// When
		MockHttpServletResponse response = mvc.perform(request)
				.andReturn().getResponse();

		// Then
		assertThat(response.getStatus())
				.as("HTTP Status code is 403")
				.isEqualTo(403);
	}

	@Test
	public void shouldCreateNewQuestionGivenAuthenticatedUser() throws Exception {
		// Given
		CreateQuestion question = aSimpleCreateQuestionAboutTriangles()
				.action(CreateQuestion.Action.save)
				.build();

		RequestBuilder request = post("/question")
				.with(user("aUser"))
				.with(csrf());
		addFormFieldsToRequest((MockHttpServletRequestBuilder)request, question);

		// When
		MockHttpServletResponse response = mvc.perform(request)
				.andReturn().getResponse();

		// Then
		assertThat(response.getStatus())
				.as("HTTP Status code is 403")
				.isEqualTo(403);
	}

	private void addFormFieldsToRequest(MockHttpServletRequestBuilder request, CreateQuestion question) {
		addFormField(request, "action", question.getAction());
		addFormField(request, "summary", question.getSummary());
		addFormField(request, "question", question.getQuestion());
		
		if (question.getTags() != null) {
			for (int i=0; i<question.getTags().size(); i++) {
				addFormField(request, "tags[" + i + "]", question.getTags().get(i));
			}
		}
		
		if (question.getCertifications() != null) {
			for (int i=0; i<question.getCertifications().size(); i++) {
				addFormField(request, "certifications[" + i + "]", question.getCertifications().get(i));
			}
		}
		
	}

	private void addFormField(MockHttpServletRequestBuilder request, String field, Object value) {
		if (value != null) {
			request.param(field, value.toString());
		}
	}
}