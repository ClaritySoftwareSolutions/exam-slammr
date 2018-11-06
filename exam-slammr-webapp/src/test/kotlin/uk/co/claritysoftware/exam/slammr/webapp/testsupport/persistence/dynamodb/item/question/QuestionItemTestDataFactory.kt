package uk.co.claritysoftware.exam.slammr.webapp.testsupport.persistence.dynamodb.item.question

import uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.item.question.AnswerDocument
import uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.item.question.FurtherReadingDocument
import uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.item.question.QuestionItem
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter.ISO_ZONED_DATE_TIME

/**
 * Test data factory for [QuestionItem] instances
 */
class QuestionItemTestDataFactory {

	companion object {

		/**
		 * @return a Question Item Builder containing a valid question item, ready to have it's build method called
		 */
		@JvmStatic
		fun aSimpleQuestionItemAboutSquares(): QuestionItem.QuestionItemBuilder {
			return QuestionItem.builder()
					.id("1234")
					.slug("square-sides-question")
					.summary("Square sides question")
					.questionText("How many sides does a square have?")
					.tags(listOf("maths", "geometry"))
					.certifications(listOf("Basic Maths"))
					.answers(listOf(AnswerDocument.builder()
							.text("Four")
							.correct(true)
							.build(),
							AnswerDocument.builder()
									.text("Three")
									.correct(false)
									.build()))
					.furtherReadings(listOf(FurtherReadingDocument.builder()
							.description("Basic Maths 101")
							.referenceLocation("http://basic.maths")
							.build()))
					.createdBy("webFederatedUserId of author")
					.createdDateTime(ZonedDateTime.parse("2018-05-09T08:12:43.456Z", ISO_ZONED_DATE_TIME))
					.updatedBy("webFederatedUserId of editor")
					.updatedDateTime(ZonedDateTime.parse("2018-05-20T18:02:12.042Z", ISO_ZONED_DATE_TIME))
					.status("APPROVED")
					.votes(10)
		}

		/**
		 * @return a Question Item Builder containing a valid question item, ready to have it's build method called
		 */
		@JvmStatic
		fun aSimpleQuestionItemAboutTriangles(): QuestionItem.QuestionItemBuilder {
			return QuestionItem.builder()
					.id("5678")
					.slug("triangle-sides-question")
					.summary("Triangle sides question")
					.questionText("How many sides does a triangle have?")
					.tags(listOf("maths", "geometry"))
					.certifications(listOf("Basic Maths"))
					.answers(listOf(
							AnswerDocument.builder()
									.text("Three")
									.correct(true)
									.build(),
							AnswerDocument.builder()
									.text("Seven")
									.correct(false)
									.build()))
					.furtherReadings(listOf(FurtherReadingDocument.builder()
							.description("Basic Maths 101")
							.referenceLocation("http://basic.maths")
							.build()))
					.createdBy("webFederatedUserId of author")
					.createdDateTime(ZonedDateTime.parse("2018-05-09T08:12:43.456Z", ISO_ZONED_DATE_TIME))
					.updatedBy("webFederatedUserId of editor")
					.updatedDateTime(ZonedDateTime.parse("2018-05-20T18:02:12.042Z", ISO_ZONED_DATE_TIME))
					.status("APPROVED")
					.votes(10)
		}
	}
}