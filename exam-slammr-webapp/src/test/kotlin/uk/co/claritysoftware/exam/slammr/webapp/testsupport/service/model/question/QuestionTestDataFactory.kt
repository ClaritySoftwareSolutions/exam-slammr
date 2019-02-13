package uk.co.claritysoftware.exam.slammr.webapp.testsupport.service.model.question

import uk.co.claritysoftware.exam.slammr.webapp.service.model.question.AnswerOption
import uk.co.claritysoftware.exam.slammr.webapp.service.model.question.FurtherReading
import uk.co.claritysoftware.exam.slammr.webapp.service.model.question.Question
import uk.co.claritysoftware.exam.slammr.webapp.service.model.question.QuestionStatus.APPROVED
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter.ISO_ZONED_DATE_TIME

/**
 * Test data factory for [Question] instances
 */
class QuestionTestDataFactory {

	companion object {

		/**
		 * @return a Question Item containing a valid question item
		 */
		@JvmStatic
		fun aSimpleQuestionAboutSquares() = Question(
				id = "1234",
				summary = "Square sides question",
				slug = "squares-sides-question",
				questionText = "How many sides does a square have?",
				tags = setOf("maths", "geometry"),
				certifications = setOf("Basic Maths"),
				answers = setOf(
						AnswerOption("Three", false),
						AnswerOption("Four", true)),
				furtherReadings = setOf(FurtherReading("Basic Maths 101", "http://basic.maths")),
				createdBy = "webFederatedUserId of author",
				createdDateTime = ZonedDateTime.parse("2018-05-09T08:12:43.456Z", ISO_ZONED_DATE_TIME),
				updatedBy = "webFederatedUserId of editor",
				updatedDateTime = ZonedDateTime.parse("2018-05-20T18:02:12.042Z", ISO_ZONED_DATE_TIME),
				status = APPROVED,
				votes = 10)


		/**
		 * @return a Question Item containing a valid question item
		 */
		@JvmStatic
		fun aSimpleQuestionAboutTriangles() = Question(
				id = "5678",
				slug = "triangle-sides-question",
				summary = "Triangle sides question",
				questionText = "How many sides does a triangle have?",
				tags = setOf("maths", "geometry"),
				certifications = setOf("Basic Maths"),
				answers = setOf(
						AnswerOption("Three", true),
						AnswerOption("Seven", false)),
				furtherReadings = setOf(FurtherReading("Basic Maths 101", "http://basic.maths")),
				createdBy = "webFederatedUserId of author",
				createdDateTime = ZonedDateTime.parse("2018-05-09T08:12:43.456Z", ISO_ZONED_DATE_TIME),
				updatedBy = "webFederatedUserId of editor",
				updatedDateTime = ZonedDateTime.parse("2018-05-20T18:02:12.042Z", ISO_ZONED_DATE_TIME),
				status = APPROVED,
				votes = 10)
	}

}