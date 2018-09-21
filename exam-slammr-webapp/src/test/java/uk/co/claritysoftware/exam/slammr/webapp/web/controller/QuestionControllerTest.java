package uk.co.claritysoftware.exam.slammr.webapp.web.controller;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static uk.co.claritysoftware.exam.slammr.webapp.testsupport.service.model.question.QuestionTestDataFactory.aSimpleQuestionAboutTriangles;
import static uk.co.claritysoftware.exam.slammr.webapp.testsupport.web.model.question.CreateQuestionTestDataFactory.aSimpleCreateQuestionAboutTriangles;
import static uk.co.claritysoftware.exam.slammr.webapp.web.model.question.AnswerOption.generateEmptyAnswerOption;
import static uk.co.claritysoftware.exam.slammr.webapp.web.model.question.CreateQuestion.generateEmptyCreateQuestion;
import static uk.co.claritysoftware.exam.slammr.webapp.web.model.question.FurtherReading.generateEmptyFurtherReading;

import java.security.Principal;
import java.util.ArrayList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import uk.co.claritysoftware.exam.slammr.webapp.service.QuestionService;
import uk.co.claritysoftware.exam.slammr.webapp.service.model.question.Question;
import uk.co.claritysoftware.exam.slammr.webapp.service.model.question.QuestionStatus;
import uk.co.claritysoftware.exam.slammr.webapp.web.model.question.AnswerOption;
import uk.co.claritysoftware.exam.slammr.webapp.web.model.question.CreateQuestion;
import uk.co.claritysoftware.exam.slammr.webapp.web.model.question.FurtherReading;

/**
 * Unit test class for {@link QuestionController}
 */
@RunWith(MockitoJUnitRunner.class)
public class QuestionControllerTest {

	@Mock
	private QuestionService questionService;

	@InjectMocks
	private QuestionController questionController;

	@Test
	public void shouldGetQuestionPage() {
		// Given
		CreateQuestion emptyQuestionForm = generateEmptyCreateQuestion();
		Model expectedModel = new ExtendedModelMap();
		expectedModel.addAttribute("form", emptyQuestionForm);
		expectedModel.addAttribute("showBindErrors", false);

		// When
		ModelAndView modelAndView = questionController.getQuestionPage();

		// Then
		assertThat(modelAndView)
				.as("Expected ModelAndView is returned")
				.extracting("viewName", "model")
				.isEqualTo(asList(
						"question/create",
						expectedModel
				));
	}

	@Test
	public void shouldNotCreateNewQuestionGivenAddTagAction() {
		// Given
		BindingResult bindingResult = mock(BindingResult.class);
		Principal principal = mock(Principal.class);
		CreateQuestion createQuestionForm = createQuestion()
				.tags(new ArrayList<>(singletonList("tag1")))
				.action(CreateQuestion.Action.addTag)
				.build();

		CreateQuestion returnedCreateQuestionForm = createQuestion()
				.tags(asList("tag1", ""))
				.action(CreateQuestion.Action.addTag)
				.build();
		Model expectedModel = new ExtendedModelMap();
		expectedModel.addAttribute("form", returnedCreateQuestionForm);
		expectedModel.addAttribute("showBindErrors", false);

		// When
		ModelAndView modelAndView = questionController.createNewQuestion(createQuestionForm, bindingResult, principal);

		// Then
		assertThat(modelAndView)
				.as("Expected ModelAndView is returned")
				.extracting("viewName", "model")
				.isEqualTo(asList(
						"question/create",
						expectedModel
				));
	}

	@Test
	public void shouldNotCreateNewQuestionGivenAddCertificationAction() {
		// Given
		BindingResult bindingResult = mock(BindingResult.class);
		Principal principal = mock(Principal.class);
		CreateQuestion createQuestionForm = createQuestion()
				.certifications(new ArrayList<>(singletonList("certification1")))
				.action(CreateQuestion.Action.addCertification)
				.build();

		CreateQuestion returnedCreateQuestionForm = createQuestion()
				.certifications(asList("certification1", ""))
				.action(CreateQuestion.Action.addCertification)
				.build();
		Model expectedModel = new ExtendedModelMap();
		expectedModel.addAttribute("form", returnedCreateQuestionForm);
		expectedModel.addAttribute("showBindErrors", false);

		// When
		ModelAndView modelAndView = questionController.createNewQuestion(createQuestionForm, bindingResult, principal);

		// Then
		assertThat(modelAndView)
				.as("Expected ModelAndView is returned")
				.extracting("viewName", "model")
				.isEqualTo(asList(
						"question/create",
						expectedModel
				));
	}

	@Test
	public void shouldNotCreateNewQuestionGivenAddFurtherReadingAction() {
		// Given
		BindingResult bindingResult = mock(BindingResult.class);
		Principal principal = mock(Principal.class);
		CreateQuestion createQuestionForm = createQuestion()
				.furtherReadings(new ArrayList<>(singletonList(FurtherReading.builder()
						.description("Some reference")
						.referenceLocation("http://some.location")
						.build())))
				.action(CreateQuestion.Action.addFurtherReading)
				.build();

		CreateQuestion returnedCreateQuestionForm = createQuestion()
				.furtherReadings(asList(FurtherReading.builder()
								.description("Some reference")
								.referenceLocation("http://some.location")
								.build(),
						FurtherReading.builder().build()))
				.action(CreateQuestion.Action.addFurtherReading)
				.build();
		Model expectedModel = new ExtendedModelMap();
		expectedModel.addAttribute("form", returnedCreateQuestionForm);
		expectedModel.addAttribute("showBindErrors", false);

		// When
		ModelAndView modelAndView = questionController.createNewQuestion(createQuestionForm, bindingResult, principal);

		// Then
		assertThat(modelAndView)
				.as("Expected ModelAndView is returned")
				.extracting("viewName", "model")
				.isEqualTo(asList(
						"question/create",
						expectedModel
				));
	}

	@Test
	public void shouldNotCreateNewQuestionGivenAddAnswerAction() {
		// Given
		BindingResult bindingResult = mock(BindingResult.class);
		Principal principal = mock(Principal.class);
		CreateQuestion createQuestionForm = createQuestion()
				.answerOptions(new ArrayList<>(singletonList(AnswerOption.builder()
						.answer("Some answser")
						.correct(true)
						.build())))
				.action(CreateQuestion.Action.addAnswer)
				.build();

		CreateQuestion returnedCreateQuestionForm = createQuestion()
				.answerOptions(asList(AnswerOption.builder()
								.answer("Some answser")
								.correct(true)
								.build(),
						AnswerOption.builder().build()))
				.action(CreateQuestion.Action.addAnswer)
				.build();
		Model expectedModel = new ExtendedModelMap();
		expectedModel.addAttribute("form", returnedCreateQuestionForm);
		expectedModel.addAttribute("showBindErrors", false);

		// When
		ModelAndView modelAndView = questionController.createNewQuestion(createQuestionForm, bindingResult, principal);

		// Then
		assertThat(modelAndView)
				.as("Expected ModelAndView is returned")
				.extracting("viewName", "model")
				.isEqualTo(asList(
						"question/create",
						expectedModel
				));
	}

	@Test
	public void shouldNotCreateNewQuestionGivenSaveActionAndBindingErrors() {
		// Given
		BindingResult bindingResult = mock(BindingResult.class);
		Principal principal = mock(Principal.class);
		CreateQuestion createQuestionForm = aSimpleCreateQuestionAboutTriangles()
				.summary(null)
				.action(CreateQuestion.Action.save)
				.build();

		given(bindingResult.hasErrors()).willReturn(true);

		Model expectedModel = new ExtendedModelMap();
		expectedModel.addAttribute("form", createQuestionForm);
		expectedModel.addAttribute("showBindErrors", true);

		// When
		ModelAndView modelAndView = questionController.createNewQuestion(createQuestionForm, bindingResult, principal);

		// Then
		assertThat(modelAndView)
				.as("Expected ModelAndView is returned")
				.extracting("viewName", "model")
				.isEqualTo(asList(
						"question/create",
						expectedModel
				));
	}

	@Test
	public void shouldCreateNewQuestionGivenSaveAction() {
		// Given
		BindingResult bindingResult = mock(BindingResult.class);
		Principal principal = mock(Principal.class);
		CreateQuestion createQuestionForm = aSimpleCreateQuestionAboutTriangles()
				.action(CreateQuestion.Action.save)
				.build();

		String username = "aUser";
		given(principal.getName()).willReturn(username);
		given(bindingResult.hasErrors()).willReturn(false);

		Question newQuestion = aSimpleQuestionAboutTriangles()
				.id(null)
				.slug(null)
				.createdBy(username)
				.updatedBy(null)
				.updatedDateTime(null)
				.status(QuestionStatus.SUBMITTED_FOR_APPROVAL)
				.votes(0)
				.build();

		Model expectedModel = new ExtendedModelMap();

		// When
		ModelAndView modelAndView = questionController.createNewQuestion(createQuestionForm, bindingResult, principal);

		// Then
		then(questionService).should().saveNewQuestion(argThat(q -> {
			assertThat(q).isEqualToIgnoringGivenFields(newQuestion, "createdDateTime");
			return true;
		}));
		assertThat(modelAndView)
				.as("Expected ModelAndView is returned")
				.extracting("viewName", "model")
				.isEqualTo(asList(
						"redirect:/",
						expectedModel
				));
	}

	private CreateQuestion.CreateQuestionBuilder createQuestion() {
		return CreateQuestion.builder()
				.answerOptions(asList(
						generateEmptyAnswerOption(),
						generateEmptyAnswerOption()
				))
				.tags(singletonList(""))
				.certifications(singletonList(""))
				.furtherReadings(singletonList(generateEmptyFurtherReading()));
	}
}