package uk.co.claritysoftware.exam.slammr.webapp.web.controller

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.argThat
import io.florianlopes.spring.test.web.servlet.request.MockMvcRequestBuilderUtils.postForm
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import uk.co.claritysoftware.exam.slammr.webapp.service.QuestionService
import uk.co.claritysoftware.exam.slammr.webapp.service.model.question.QuestionStatus.SUBMITTED_FOR_APPROVAL
import uk.co.claritysoftware.exam.slammr.webapp.testsupport.service.model.question.QuestionTestDataFactory.Companion.aSimpleQuestionAboutTriangles
import uk.co.claritysoftware.exam.slammr.webapp.testsupport.web.model.question.CreateQuestionTestDataFactory.Companion.aSimpleCreateQuestionAboutTriangles
import uk.co.claritysoftware.exam.slammr.webapp.web.model.question.CreateQuestion
import java.util.*

/**
 * MockMvc test class for [QuestionController]
 */
@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("classpath:test-context.properties")
class QuestionControllerMockMvcTest {

	@Autowired
	private lateinit var mvc: MockMvc

	@MockBean
	private lateinit var questionService: QuestionService

	@Test
	@Throws(Exception::class)
	fun shouldNotGetQuestionPageGivenUnauthenticatedUser() {
		// Given
		val request = get("/question/new")

		// When
		val response = mvc.perform(request)
				.andReturn().response

		// Then
		assertThat(response.status)
				.`as`("HTTP Status code is 403")
				.isEqualTo(403)
	}

	@Test
	@Throws(Exception::class)
	fun shouldGetQuestionPageGivenAuthenticatedUser() {
		// Given
		val request = get("/question/new")
				.with(user("aUser"))

		// When
		val response = mvc.perform(request)
				.andReturn().response

		// Then
		assertThat(response.status)
				.`as`("HTTP Status code is 200")
				.isEqualTo(200)
	}

	@Test
	@Throws(Exception::class)
	fun shouldNotCreateNewQuestionGivenUnauthenticatedUser() {
		// Given
		val question = aSimpleCreateQuestionAboutTriangles()

		val request = postForm("/question", question)
				.with(csrf())

		// When
		val response = mvc.perform(request)
				.andReturn().response

		// Then
		assertThat(response.status)
				.`as`("HTTP Status code is 403")
				.isEqualTo(403)
	}

	@Test
	@Throws(Exception::class)
	fun shouldCreateNewQuestionGivenAuthenticatedUser() {
		// Given
		val userName = "aUser"

		val createQuestion = aSimpleCreateQuestionAboutTriangles()
				.copy(action = CreateQuestion.Action.save)

		val newQuestion = aSimpleQuestionAboutTriangles()
				.copy(id = null, slug = null, createdBy = userName, updatedBy = null, updatedDateTime = null, status = SUBMITTED_FOR_APPROVAL, votes = 0)

		val savedQuestion = aSimpleQuestionAboutTriangles()
				.copy(createdBy = userName, updatedBy = null, updatedDateTime = null, status = SUBMITTED_FOR_APPROVAL)

		given(questionService.saveNewQuestion(any()))
				.willReturn(savedQuestion)

		val request = postForm("/question", createQuestion)
				.with(user(userName))
				.with(csrf())

		// When
		val response = mvc.perform(request)
				.andReturn().response

		// Then
		then(questionService).should().saveNewQuestion(argThat {
			assertThat(this).isEqualToIgnoringGivenFields(newQuestion, "createdDateTime")
			true
		})
		assertThat(response.status)
				.`as`("HTTP Status code is 302")
				.isEqualTo(302)
		assertThat(response.getHeader("Location"))
				.`as`("Location header is for the newly saved question")
				.isEqualTo("/question/5678/triangle-sides-question")
	}

	@Test
	@Throws(Exception::class)
	fun shouldNotCreateNewQuestionGivenSaveActionAndBindingErrors() {
		// Given
		val userName = "aUser"

		val createQuestion = aSimpleCreateQuestionAboutTriangles()
				.copy(summary = "", action = CreateQuestion.Action.save)


		val request = postForm("/question", createQuestion)
				.with(user(userName))
				.with(csrf())

		// When
		val response = mvc.perform(request)
				.andReturn().response

		// Then
		then(questionService).shouldHaveZeroInteractions()
		assertThat(response.status)
				.`as`("HTTP Status code is 200")
				.isEqualTo(200)
	}

	@Test
	@Throws(Exception::class)
	fun shouldGetExistingQuestionPage() {
		// Given
		val id = "5678"
		val slug = "triangle-sides-question"
		val request = get("/question/$id/$slug")

		val question = aSimpleQuestionAboutTriangles()

		given(questionService.getQuestionById(any()))
				.willReturn(Optional.of(question))

		// When
		val response = mvc.perform(request)
				.andReturn().response

		// Then
		then(questionService).should().getQuestionById(id)
		assertThat(response.status)
				.`as`("HTTP Status code is 200")
				.isEqualTo(200)
	}

	@Test
	@Throws(Exception::class)
	fun shouldFailToGetExistingQuestionPageGivenNonExistentQuestionId() {
		// Given
		val id = "1234"
		val slug = "triangle-sides-question"
		val request = get("/question/$id/$slug")

		given(questionService.getQuestionById(any()))
				.willReturn(Optional.empty())

		// When
		val response = mvc.perform(request)
				.andReturn().response

		// Then
		then(questionService).should().getQuestionById(id)
		assertThat(response.status)
				.`as`("HTTP Status code is 404")
				.isEqualTo(404)
	}

	@Test
	@Throws(Exception::class)
	fun shouldNotGetExistingQuestionPageGivenIdWithWrongSlug() {
		// Given
		val id = "5678"
		val slug = "the-wrong-slug"
		val request = get("/question/$id/$slug")

		val question = aSimpleQuestionAboutTriangles()

		given(questionService.getQuestionById(any()))
				.willReturn(Optional.of(question))

		// When
		val response = mvc.perform(request)
				.andReturn().response

		// Then
		then(questionService).should().getQuestionById(id)
		assertThat(response.status)
				.`as`("HTTP Status code is 302")
				.isEqualTo(302)
		assertThat(response.getHeader("Location"))
				.`as`("Location header is for the newly saved question")
				.isEqualTo("/question/5678/triangle-sides-question")
	}

	@Test
	@Throws(Exception::class)
	fun shouldNotGetExistingQuestionPageGivenIdWithNoSlug() {
		// Given
		val id = "5678"
		val slug = ""
		val request = get("/question/$id/$slug")

		val question = aSimpleQuestionAboutTriangles()

		given(questionService.getQuestionById(any()))
				.willReturn(Optional.of(question))

		// When
		val response = mvc.perform(request)
				.andReturn().response

		// Then
		then(questionService).should().getQuestionById(id)
		assertThat(response.status)
				.`as`("HTTP Status code is 302")
				.isEqualTo(302)
		assertThat(response.getHeader("Location"))
				.`as`("Location header is for the newly saved question")
				.isEqualTo("/question/5678/triangle-sides-question")
	}

}