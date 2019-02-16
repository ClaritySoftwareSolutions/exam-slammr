package uk.co.claritysoftware.exam.slammr.webapp.service.factory

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import uk.co.claritysoftware.exam.slammr.webapp.testsupport.persistence.dynamodb.item.question.QuestionItemTestDataFactory.Companion.aSimpleQuestionItemAboutTriangles
import uk.co.claritysoftware.exam.slammr.webapp.testsupport.service.model.question.QuestionTestDataFactory.Companion.aSimpleQuestionAboutTriangles

/**
 * Unit test class for [QuestionFactory]
 */
class QuestionFactoryTest {

	@Test
	fun shouldDeriveQuestionValueOfGivenQuestionItem() {
		// Given
		val questionItem = aSimpleQuestionItemAboutTriangles()
		val expectedQuestion = aSimpleQuestionAboutTriangles()

		// When
		val question = QuestionFactory.valueOf(questionItem)

		// Then
		assertThat(question)
				.isEqualTo(expectedQuestion)
	}

	@Test
	fun shouldDeriveQuestionItemValueOfGivenQuestion() {
		// Given
		val question = aSimpleQuestionAboutTriangles()
		val slug = "triangle-sides-question"
		val expectedQuestionItem = aSimpleQuestionItemAboutTriangles()
				.copy(id = question.id, slug = slug)

		// When
		val questionItem = QuestionFactory.valueOf(question, slug)

		// Then
		assertThat(questionItem)
				.isEqualTo(expectedQuestionItem)
	}

}