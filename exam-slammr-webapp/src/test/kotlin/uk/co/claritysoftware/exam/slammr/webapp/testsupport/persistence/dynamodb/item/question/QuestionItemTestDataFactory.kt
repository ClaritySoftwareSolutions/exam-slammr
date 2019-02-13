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
		 * @return a Question Item containing a valid question item
		 */
		@JvmStatic
		fun aSimpleQuestionItemAboutSquares() = QuestionItem(
				id = "1234",
				slug = "square-sides-question",
				summary = "Square sides question",
				questionText = "How many sides does a square have?",
				tags = listOf("maths", "geometry"),
				certifications = listOf("Basic Maths"),
				answers = listOf(AnswerDocument("Three", false),
						AnswerDocument("Four", true)),
				furtherReadings = listOf(FurtherReadingDocument("Basic Maths 101", "http://basic.maths")),
				createdBy = "webFederatedUserId of author",
				createdDateTime = ZonedDateTime.parse("2018-05-09T08:12:43.456Z", ISO_ZONED_DATE_TIME),
				updatedBy = "webFederatedUserId of editor",
				updatedDateTime = ZonedDateTime.parse("2018-05-20T18:02:12.042Z", ISO_ZONED_DATE_TIME),
				status = "APPROVED",
				votes = 10)

		/**
		 * @return a Question Item containing a valid question item
		 */
		@JvmStatic
		fun aSimpleQuestionItemAboutTriangles() = QuestionItem(
				id = "5678",
				slug = "triangle-sides-question",
				summary = "Triangle sides question",
				questionText = "How many sides does a triangle have?",
				tags = listOf("maths", "geometry"),
				certifications = listOf("Basic Maths"),
				answers = listOf(
						AnswerDocument("Three", true),
						AnswerDocument("Seven", false)),
				furtherReadings = listOf(FurtherReadingDocument("Basic Maths 101", "http://basic.maths")),
				createdBy = "webFederatedUserId of author",
				createdDateTime = ZonedDateTime.parse("2018-05-09T08:12:43.456Z", ISO_ZONED_DATE_TIME),
				updatedBy = "webFederatedUserId of editor",
				updatedDateTime = ZonedDateTime.parse("2018-05-20T18:02:12.042Z", ISO_ZONED_DATE_TIME),
				status = "APPROVED",
				votes = 10)
	}
}