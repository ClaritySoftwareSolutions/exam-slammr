package uk.co.claritysoftware.exam.slammr.webapp.web.controller;

import static io.florianlopes.spring.test.web.servlet.request.MockMvcRequestBuilderUtils.postForm;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static uk.co.claritysoftware.exam.slammr.webapp.testsupport.service.model.question.QuestionTestDataFactoryKt.aSimpleQuestionAboutTriangles;
import static uk.co.claritysoftware.exam.slammr.webapp.testsupport.web.model.question.CreateQuestionTestDataFactoryKt.aSimpleCreateQuestionAboutTriangles;

import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import uk.co.claritysoftware.exam.slammr.webapp.service.QuestionService;
import uk.co.claritysoftware.exam.slammr.webapp.service.model.question.Question;
import uk.co.claritysoftware.exam.slammr.webapp.service.model.question.QuestionStatus;
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

    @MockBean
    private QuestionService questionService;

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

        RequestBuilder request = postForm("/question", question)
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
        String userName = "aUser";

        CreateQuestion createQuestion = aSimpleCreateQuestionAboutTriangles()
                .action(CreateQuestion.Action.save)
                .build();

        Question newQuestion = aSimpleQuestionAboutTriangles()
                .id(null)
                .slug(null)
                .createdBy(userName)
                .updatedBy(null)
                .updatedDateTime(null)
                .status(QuestionStatus.SUBMITTED_FOR_APPROVAL)
                .votes(0)
                .build();
        Question savedQuestion = aSimpleQuestionAboutTriangles()
                .createdBy(userName)
                .updatedBy(null)
                .updatedDateTime(null)
                .status(QuestionStatus.SUBMITTED_FOR_APPROVAL)
                .votes(0)
                .build();
        given(questionService.saveNewQuestion(any()))
                .willReturn(savedQuestion);

        RequestBuilder request = postForm("/question", createQuestion)
                .with(user(userName))
                .with(csrf());

        // When
        MockHttpServletResponse response = mvc.perform(request)
                .andReturn().getResponse();

        // Then
        then(questionService).should().saveNewQuestion(argThat(q -> {
            assertThat(q).isEqualToIgnoringGivenFields(newQuestion, "createdDateTime");
            return true;
        }));
        assertThat(response.getStatus())
                .as("HTTP Status code is 302")
                .isEqualTo(302);
        assertThat(response.getHeader("Location"))
                .as("Location header is for the newly saved question")
                .isEqualTo("/question/5678/triangle-sides-question");
    }

    @Test
    public void shouldNotCreateNewQuestionGivenSaveActionAndBindingErrors() throws Exception {
        // Given
        String userName = "aUser";

        CreateQuestion createQuestion = aSimpleCreateQuestionAboutTriangles()
				.summary(null)
				.action(CreateQuestion.Action.save)
				.build();

        RequestBuilder request = postForm("/question", createQuestion)
                .with(user(userName))
                .with(csrf());

        // When
        MockHttpServletResponse response = mvc.perform(request)
                .andReturn().getResponse();

        // Then
        then(questionService).shouldHaveZeroInteractions();
        assertThat(response.getStatus())
                .as("HTTP Status code is 200")
                .isEqualTo(200);
    }

    @Test
	public void shouldGetExistingQuestionPage() throws Exception {
    	// Given
		String id = "5678";
		String slug = "triangle-sides-question";
		RequestBuilder request = get("/question/" + id + "/" + slug);

		Question question = aSimpleQuestionAboutTriangles().build();

		given(questionService.getQuestionById(any()))
				.willReturn(Optional.of(question));

		// When
		MockHttpServletResponse response = mvc.perform(request)
				.andReturn().getResponse();

		// Then
		then(questionService).should().getQuestionById(id);
		assertThat(response.getStatus())
				.as("HTTP Status code is 200")
				.isEqualTo(200);
	}

    @Test
	public void shouldFailToGetExistingQuestionPageGivenNonExistentQuestionId() throws Exception {
    	// Given
		String id = "1234";
		String slug = "triangle-sides-question";
		RequestBuilder request = get("/question/" + id + "/" + slug);

		given(questionService.getQuestionById(any()))
				.willReturn(Optional.empty());

		// When
		MockHttpServletResponse response = mvc.perform(request)
				.andReturn().getResponse();

		// Then
		then(questionService).should().getQuestionById(id);
		assertThat(response.getStatus())
				.as("HTTP Status code is 404")
				.isEqualTo(404);
	}

	@Test
	public void shouldNotGetExistingQuestionPageGivenIdWithWrongSlug() throws Exception {
		// Given
		String id = "5678";
		String slug = "the-wrong-slug";
		RequestBuilder request = get("/question/" + id + "/" + slug);

		Question question = aSimpleQuestionAboutTriangles().build();

		given(questionService.getQuestionById(any()))
				.willReturn(Optional.of(question));

		// When
		MockHttpServletResponse response = mvc.perform(request)
				.andReturn().getResponse();

		// Then
		then(questionService).should().getQuestionById(id);
		assertThat(response.getStatus())
				.as("HTTP Status code is 302")
				.isEqualTo(302);
		assertThat(response.getHeader("Location"))
				.as("Location header is for the newly saved question")
				.isEqualTo("/question/5678/triangle-sides-question");
	}

	@Test
	public void shouldNotGetExistingQuestionPageGivenIdWithNoSlug() throws Exception {
		// Given
		String id = "5678";
		String slug = "";
		RequestBuilder request = get("/question/" + id + "/" + slug);

		Question question = aSimpleQuestionAboutTriangles().build();

		given(questionService.getQuestionById(any()))
				.willReturn(Optional.of(question));

		// When
		MockHttpServletResponse response = mvc.perform(request)
				.andReturn().getResponse();

		// Then
		then(questionService).should().getQuestionById(id);
		assertThat(response.getStatus())
				.as("HTTP Status code is 302")
				.isEqualTo(302);
		assertThat(response.getHeader("Location"))
				.as("Location header is for the newly saved question")
				.isEqualTo("/question/5678/triangle-sides-question");
	}
}