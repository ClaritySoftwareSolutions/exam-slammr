package uk.co.claritysoftware.exam.slammr.webapp.testsupport.service.model.question

import com.google.common.collect.Sets.newHashSet
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
		 * @return a Question Item Builder containing a valid question item, ready to have it's build method called
		 */
		@JvmStatic
		fun aSimpleQuestionAboutSquares(): Question.QuestionBuilder {
			return Question.builder()
					.id("1234")
					.summary("Square sides question")
					.questionText("How many sides does a square have?")
					.tags(newHashSet("maths", "geometry"))
					.certifications(newHashSet("Basic Maths"))
					.answers(newHashSet(
							AnswerOption("Three", false),
							AnswerOption("Four", true)))
					.furtherReadings(newHashSet(FurtherReading("Basic Maths 101", "http://basic.maths")))
					.createdBy("webFederatedUserId of author")
					.createdDateTime(ZonedDateTime.parse("2018-05-09T08:12:43.456Z", ISO_ZONED_DATE_TIME))
					.updatedBy("webFederatedUserId of editor")
					.updatedDateTime(ZonedDateTime.parse("2018-05-20T18:02:12.042Z", ISO_ZONED_DATE_TIME))
					.status(APPROVED)
					.votes(10)
		}

		/**
		 * @return a Question Item Builder containing a valid question item, ready to have it's build method called
		 */
		@JvmStatic
		fun aSimpleQuestionAboutTriangles(): Question.QuestionBuilder {
			return Question.builder()
					.id("5678")
					.slug("triangle-sides-question")
					.summary("Triangle sides question")
					.questionText("How many sides does a triangle have?")
					.tags(newHashSet("maths", "geometry"))
					.certifications(newHashSet("Basic Maths"))
					.answers(newHashSet(
							AnswerOption("Three", true),
							AnswerOption("Seven", false)))
					.furtherReadings(newHashSet(FurtherReading("Basic Maths 101", "http://basic.maths")))
					.createdBy("webFederatedUserId of author")
					.createdDateTime(ZonedDateTime.parse("2018-05-09T08:12:43.456Z", ISO_ZONED_DATE_TIME))
					.updatedBy("webFederatedUserId of editor")
					.updatedDateTime(ZonedDateTime.parse("2018-05-20T18:02:12.042Z", ISO_ZONED_DATE_TIME))
					.status(APPROVED)
					.votes(10)
		}
	}
}