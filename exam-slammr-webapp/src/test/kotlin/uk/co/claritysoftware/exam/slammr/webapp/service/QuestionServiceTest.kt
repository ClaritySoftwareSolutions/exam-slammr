package uk.co.claritysoftware.exam.slammr.webapp.service

import com.nhaarman.mockitokotlin2.any
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.item.question.QuestionItem
import uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.repository.DynamoDbQuestionItemRepository
import uk.co.claritysoftware.exam.slammr.webapp.testsupport.persistence.dynamodb.item.question.QuestionItemTestDataFactory.Companion.aSimpleQuestionItemAboutSquares
import uk.co.claritysoftware.exam.slammr.webapp.testsupport.service.model.question.QuestionTestDataFactory.Companion.aSimpleQuestionAboutSquares
import java.util.*

/**
 * Unit test class for [QuestionService]
 */
@RunWith(MockitoJUnitRunner::class)
class QuestionServiceTest {

	@Mock
	private lateinit var questionItemRepository: DynamoDbQuestionItemRepository

	@InjectMocks
	private lateinit var questionService: QuestionService

	@Test
	fun shouldSaveNewQuestion() {
		// Given
		val id = UUID.randomUUID().toString()

		val newQuestion = aSimpleQuestionAboutSquares()
				.copy(id = null)

		val newQuestionItem = aSimpleQuestionItemAboutSquares()
				.copy(id = null, slug = "square-sides-question")

		val savedQuestion = aSimpleQuestionAboutSquares()
				.copy(id = id, slug = "square-sides-question")

		val savedQuestionItem = aSimpleQuestionItemAboutSquares()
				.copy(id = id, slug = "square-sides-question")

		given(questionItemRepository.save(any<QuestionItem>()))
				.willReturn(savedQuestionItem)

		// When
		val question = questionService.saveNewQuestion(newQuestion)

		// Then
		then(questionItemRepository).should().save(newQuestionItem)
		assertThat(question)
				.isEqualTo(savedQuestion)
	}

	@Test
	fun shouldFailToSaveNewQuestionGivenQuestionWithId() {
		// Given
		val id = UUID.randomUUID().toString()

		val newQuestion = aSimpleQuestionAboutSquares()
				.copy(id = id)

		// When
		val throwable = catchThrowable { questionService.saveNewQuestion(newQuestion) }

		// Then
		then<DynamoDbQuestionItemRepository>(questionItemRepository).shouldHaveZeroInteractions()
		assertThat(throwable)
				.isInstanceOf(IllegalArgumentException::class.java)
				.hasMessage("Cannot use this method to save an existing Question")
	}

	@Test
	fun shouldGetQuestionById() {
		// Given
		val id = "1234"

		val questionItem = aSimpleQuestionItemAboutSquares()
				.copy(id = id, slug = "square-sides-question")

		val expectedQuestion = aSimpleQuestionAboutSquares()
				.copy(id = id, slug = "square-sides-question")

		given(questionItemRepository.findOne(any()))
				.willReturn(questionItem)

		// When
		val question = questionService.getQuestionById(id)

		// Then
		then(questionItemRepository).should().findOne(id)
		assertThat(question)
				.`as`("Optional contains a value")
				.isPresent
				.get()
				.`as`("Question is returned")
				.isEqualTo(expectedQuestion)
	}

	@Test
	fun shouldNotGetQuestionByIdGivenNonExistentId() {
		// Given
		val id = "9999"

		given(questionItemRepository.findOne(any()))
				.willReturn(null)

		// When
		val question = questionService.getQuestionById(id)

		// Then
		then(questionItemRepository).should().findOne(id)
		assertThat(question)
				.`as`("Optional is empty")
				.isNotPresent
	}
}