package uk.co.claritysoftware.exam.slammr.webapp.web.factory

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import uk.co.claritysoftware.exam.slammr.webapp.service.model.question.QuestionStatus.SUBMITTED_FOR_APPROVAL
import uk.co.claritysoftware.exam.slammr.webapp.testsupport.service.model.question.QuestionTestDataFactory.Companion.aSimpleQuestionAboutTriangles
import uk.co.claritysoftware.exam.slammr.webapp.testsupport.web.model.question.CreateQuestionTestDataFactory.Companion.aSimpleCreateQuestionAboutTriangles
import java.time.ZoneOffset
import java.time.ZonedDateTime

/**
 * Unit test class for [CreateQuestionFactory]
 */
class CreateQuestionFactoryTest {

	@Test
	fun shouldDeriveQuestionItemValueOfGivenQuestion() {
		// Given
		val createQuestion = aSimpleCreateQuestionAboutTriangles()
		val authorId = "1234"
		val expectedQuestion = aSimpleQuestionAboutTriangles()
				.copy(id = null, slug = null, createdBy = authorId, updatedBy = null, updatedDateTime = null, status = SUBMITTED_FOR_APPROVAL, votes = 0)

		val earliestCreatedDateTime = ZonedDateTime.now().withZoneSameInstant(ZoneOffset.UTC)

		// When
		val question = CreateQuestionFactory.valueOf(createQuestion, authorId)

		// Then
		val latestCreatedDateTime = ZonedDateTime.now().withZoneSameInstant(ZoneOffset.UTC)
		assertThat(question)
				.isEqualToIgnoringGivenFields(expectedQuestion, "createdDateTime")
		assertThat(question.createdDateTime)
				.isAfterOrEqualTo(earliestCreatedDateTime)
				.isBeforeOrEqualTo(latestCreatedDateTime)
	}

}