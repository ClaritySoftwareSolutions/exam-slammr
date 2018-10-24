package uk.co.claritysoftware.exam.slammr.webapp.testsupport.service.model.question;

import static com.google.common.collect.Sets.newHashSet;
import static java.time.format.DateTimeFormatter.ISO_ZONED_DATE_TIME;
import static uk.co.claritysoftware.exam.slammr.webapp.service.model.question.QuestionStatus.APPROVED;

import java.time.ZonedDateTime;
import uk.co.claritysoftware.exam.slammr.webapp.service.model.question.AnswerOption;
import uk.co.claritysoftware.exam.slammr.webapp.service.model.question.FurtherReading;
import uk.co.claritysoftware.exam.slammr.webapp.service.model.question.Question;

/**
 * Test data factory for {@link Question} instances
 */
public class QuestionTestDataFactory {

	/**
	 * @return a Question Item Builder containing a valid question item, ready to have it's build method called
	 */
	public static Question.QuestionBuilder aSimpleQuestionAboutSquares() {
		return Question.builder()
				.id("1234")
				.summary("Square sides question")
				.questionText("How many sides does a square have?")
				.tags(newHashSet("maths", "geometry"))
				.certifications(newHashSet("Basic Maths"))
				.answers(newHashSet(
						new AnswerOption("Three", false),
						new AnswerOption("Four", true)))
				.furtherReadings(newHashSet(new FurtherReading("Basic Maths 101","http://basic.maths")))
				.createdBy("webFederatedUserId of author")
				.createdDateTime(ZonedDateTime.parse("2018-05-09T08:12:43.456Z", ISO_ZONED_DATE_TIME))
				.updatedBy("webFederatedUserId of editor")
				.updatedDateTime(ZonedDateTime.parse("2018-05-20T18:02:12.042Z", ISO_ZONED_DATE_TIME))
				.status(APPROVED)
				.votes(10);
	}

	/**
	 * @return a Question Item Builder containing a valid question item, ready to have it's build method called
	 */
	public static Question.QuestionBuilder aSimpleQuestionAboutTriangles() {
		return Question.builder()
				.id("5678")
				.slug("triangle-sides-question")
				.summary("Triangle sides question")
				.questionText("How many sides does a triangle have?")
				.tags(newHashSet("maths", "geometry"))
				.certifications(newHashSet("Basic Maths"))
				.answers(newHashSet(
						new AnswerOption("Three", true),
						new AnswerOption("Seven", false)))
				.furtherReadings(newHashSet(new FurtherReading("Basic Maths 101", "http://basic.maths")))
				.createdBy("webFederatedUserId of author")
				.createdDateTime(ZonedDateTime.parse("2018-05-09T08:12:43.456Z", ISO_ZONED_DATE_TIME))
				.updatedBy("webFederatedUserId of editor")
				.updatedDateTime(ZonedDateTime.parse("2018-05-20T18:02:12.042Z", ISO_ZONED_DATE_TIME))
				.status(APPROVED)
				.votes(10);
	}

}