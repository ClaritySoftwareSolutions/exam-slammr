package uk.co.claritysoftware.exam.slammr.webapp.web.controller

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.argThat
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import org.springframework.ui.ExtendedModelMap
import org.springframework.validation.BindingResult
import uk.co.claritysoftware.exam.slammr.webapp.service.QuestionService
import uk.co.claritysoftware.exam.slammr.webapp.service.model.question.QuestionStatus.SUBMITTED_FOR_APPROVAL
import uk.co.claritysoftware.exam.slammr.webapp.testsupport.service.model.question.QuestionTestDataFactory.Companion.aSimpleQuestionAboutTriangles
import uk.co.claritysoftware.exam.slammr.webapp.testsupport.web.model.question.CreateQuestionTestDataFactory.Companion.aSimpleCreateQuestionAboutTriangles
import uk.co.claritysoftware.exam.slammr.webapp.web.exception.QuestionNotFoundException
import uk.co.claritysoftware.exam.slammr.webapp.web.model.question.AnswerOption
import uk.co.claritysoftware.exam.slammr.webapp.web.model.question.CreateQuestion
import uk.co.claritysoftware.exam.slammr.webapp.web.model.question.FurtherReading
import java.security.Principal
import java.util.*

/**
 * Unit test class for [QuestionController]
 */
@RunWith(MockitoJUnitRunner::class)
class QuestionControllerTest {

	@Mock
	private val questionService: QuestionService? = null

	@InjectMocks
	private val questionController: QuestionController? = null

	@Test
	fun shouldGetQuestionPage() {
		// Given
		val emptyQuestionForm = CreateQuestion()
		val expectedModel = ExtendedModelMap()
		expectedModel.addAttribute("form", emptyQuestionForm)
		expectedModel.addAttribute("showBindErrors", false)

		// When
		val modelAndView = questionController!!.getQuestionPage()

		// Then
		assertThat(modelAndView)
				.`as`("Expected ModelAndView is returned")
				.extracting("viewName", "model")
				.isEqualTo(listOf(
						"question/create",
						expectedModel
				))
	}

	@Test
	fun shouldNotCreateNewQuestionGivenAddTagAction() {
		// Given
		val bindingResult = mock(BindingResult::class.java)
		val principal = mock(Principal::class.java)
		val createQuestionForm = CreateQuestion(
				tags = mutableListOf("tag1"),
				action = CreateQuestion.Action.addTag)

		val returnedCreateQuestionForm = CreateQuestion(
				tags = mutableListOf("tag1", ""),
				action = CreateQuestion.Action.addTag)

		val expectedModel = ExtendedModelMap()
		expectedModel.addAttribute("form", returnedCreateQuestionForm)
		expectedModel.addAttribute("showBindErrors", false)

		// When
		val modelAndView = questionController!!.createNewQuestion(createQuestionForm, bindingResult, principal)

		// Then
		assertThat(modelAndView)
				.`as`("Expected ModelAndView is returned")
				.extracting("viewName", "model")
				.isEqualTo(listOf(
						"question/create",
						expectedModel
				))
	}

	@Test
	fun shouldNotCreateNewQuestionGivenAddCertificationAction() {
		// Given
		val bindingResult = mock(BindingResult::class.java)
		val principal = mock(Principal::class.java)
		val createQuestionForm = CreateQuestion(
				certifications = mutableListOf("certification1"),
				action = CreateQuestion.Action.addCertification)

		val returnedCreateQuestionForm = CreateQuestion(
				certifications = mutableListOf("certification1", ""),
				action = CreateQuestion.Action.addCertification)
		val expectedModel = ExtendedModelMap()
		expectedModel.addAttribute("form", returnedCreateQuestionForm)
		expectedModel.addAttribute("showBindErrors", false)

		// When
		val modelAndView = questionController!!.createNewQuestion(createQuestionForm, bindingResult, principal)

		// Then
		assertThat(modelAndView)
				.`as`("Expected ModelAndView is returned")
				.extracting("viewName", "model")
				.isEqualTo(listOf(
						"question/create",
						expectedModel
				))
	}

	@Test
	fun shouldNotCreateNewQuestionGivenAddFurtherReadingAction() {
		// Given
		val bindingResult = mock(BindingResult::class.java)
		val principal = mock(Principal::class.java)
		val createQuestionForm = CreateQuestion(
				furtherReadings = mutableListOf(FurtherReading("Some reference", "http://some.location")),
				action = CreateQuestion.Action.addFurtherReading)

		val returnedCreateQuestionForm = CreateQuestion(
				furtherReadings = mutableListOf(FurtherReading("Some reference", "http://some.location"),
						FurtherReading()),
				action = CreateQuestion.Action.addFurtherReading)
		val expectedModel = ExtendedModelMap()
		expectedModel.addAttribute("form", returnedCreateQuestionForm)
		expectedModel.addAttribute("showBindErrors", false)

		// When
		val modelAndView = questionController!!.createNewQuestion(createQuestionForm, bindingResult, principal)

		// Then
		assertThat(modelAndView)
				.`as`("Expected ModelAndView is returned")
				.extracting("viewName", "model")
				.isEqualTo(listOf(
						"question/create",
						expectedModel
				))
	}

	@Test
	fun shouldNotCreateNewQuestionGivenAddAnswerAction() {
		// Given
		val bindingResult = mock(BindingResult::class.java)
		val principal = mock(Principal::class.java)
		val createQuestionForm = CreateQuestion(
				answerOptions = mutableListOf(AnswerOption("Some answser", true)),
				action = CreateQuestion.Action.addAnswer)

		val returnedCreateQuestionForm = CreateQuestion(
				answerOptions = mutableListOf(AnswerOption("Some answser", true),
						AnswerOption()),
				action = CreateQuestion.Action.addAnswer)

		val expectedModel = ExtendedModelMap()
		expectedModel.addAttribute("form", returnedCreateQuestionForm)
		expectedModel.addAttribute("showBindErrors", false)

		// When
		val modelAndView = questionController!!.createNewQuestion(createQuestionForm, bindingResult, principal)

		// Then
		assertThat(modelAndView)
				.`as`("Expected ModelAndView is returned")
				.extracting("viewName", "model")
				.isEqualTo(listOf(
						"question/create",
						expectedModel
				))
	}

	@Test
	fun shouldNotCreateNewQuestionGivenSaveActionAndBindingErrors() {
		// Given
		val bindingResult = mock(BindingResult::class.java)
		val principal = mock(Principal::class.java)
		val createQuestionForm = aSimpleCreateQuestionAboutTriangles()
				.copy(summary = "", action = CreateQuestion.Action.save)

		given(bindingResult.hasErrors()).willReturn(true)

		val expectedModel = ExtendedModelMap()
		expectedModel.addAttribute("form", createQuestionForm)
		expectedModel.addAttribute("showBindErrors", true)

		// When
		val modelAndView = questionController!!.createNewQuestion(createQuestionForm, bindingResult, principal)

		// Then
		assertThat(modelAndView)
				.`as`("Expected ModelAndView is returned")
				.extracting("viewName", "model")
				.isEqualTo(listOf(
						"question/create",
						expectedModel
				))
	}

	@Test
	fun shouldCreateNewQuestionGivenSaveAction() {
		// Given
		val bindingResult = mock(BindingResult::class.java)
		val principal = mock(Principal::class.java)
		val createQuestionForm = aSimpleCreateQuestionAboutTriangles()
				.copy(action = CreateQuestion.Action.save)

		val username = "aUser"
		given(principal.getName()).willReturn(username)
		given(bindingResult.hasErrors()).willReturn(false)

		val newQuestion = aSimpleQuestionAboutTriangles()
				.copy(id = null, slug = null, createdBy = username, updatedBy = null, updatedDateTime = null, status = SUBMITTED_FOR_APPROVAL, votes = 0)

		val savedQuestion = aSimpleQuestionAboutTriangles()
				.copy(createdBy = username, updatedBy = null, updatedDateTime = null, status = SUBMITTED_FOR_APPROVAL, votes = 0)
		given(questionService!!.saveNewQuestion(any()))
				.willReturn(savedQuestion)

		val expectedModel = ExtendedModelMap()

		// When
		val modelAndView = questionController!!.createNewQuestion(createQuestionForm, bindingResult, principal)

		// Then
		then(questionService).should().saveNewQuestion(argThat {
			assertThat(this).isEqualToIgnoringGivenFields(newQuestion, "createdDateTime")
			true
		})
		assertThat(modelAndView)
				.`as`("Expected ModelAndView is returned")
				.extracting("viewName", "model")
				.isEqualTo(listOf(
						"redirect:/question/5678/triangle-sides-question",
						expectedModel
				))
	}

	@Test
	fun shouldGetExistingQuestion() {
		// Given
		val questionId = "5678"
		val slug = "triangle-sides-question"

		val question = aSimpleQuestionAboutTriangles()
		given(questionService!!.getQuestionById(anyString()))
				.willReturn(Optional.of(question))

		val expectedModel = ExtendedModelMap()
		expectedModel.addAttribute("question", question)

		// When
		val modelAndView = questionController!!.getExistingQuestionPage(questionId, slug)

		// Then
		then(questionService).should().getQuestionById(questionId)
		assertThat(modelAndView)
				.`as`("Expected ModelAndView is returned")
				.extracting("viewName", "model")
				.isEqualTo(listOf(
						"question/view",
						expectedModel
				))
	}

	@Test
	fun shouldNotGetExistingQuestionGivenNonExistentQuestionId() {
		// Given
		val questionId = "1234"
		val slug = "triangle-sides-question"

		given(questionService!!.getQuestionById(anyString()))
				.willReturn(Optional.empty())

		// When
		val throwable = catchThrowable { questionController!!.getExistingQuestionPage(questionId, slug) }

		// Then
		then(questionService).should().getQuestionById(questionId)
		assertThat(throwable)
				.isInstanceOf(QuestionNotFoundException::class.java)
				.hasMessage("Question with id 1234 not found")
	}
}