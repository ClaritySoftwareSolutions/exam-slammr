package uk.co.claritysoftware.exam.slammr.webapp.testsupport.persistence.dynamodb.item.question;

import static java.time.format.DateTimeFormatter.ISO_ZONED_DATE_TIME;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

import java.time.ZonedDateTime;
import uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.item.question.AnswerDocument;
import uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.item.question.FurtherReadingDocument;
import uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.item.question.QuestionItem;

/**
 * Test data factory for {@link QuestionItem} instances
 */
public class QuestionItemTestDataFactory {

	/**
	 * @return a Question Item Builder containing a valid question item, ready to have it's build method called
	 */
	public static QuestionItem.QuestionItemBuilder aSimpleQuestionItemAboutSquares() {
		return QuestionItem.builder()
				.id("1234")
				.slug("square-sides-question")
				.summary("Square sides question")
				.questionText("How many sides does a square have?")
				.tags(asList("maths", "geometry"))
				.certifications(singletonList("Basic Maths"))
				.answers(asList(AnswerDocument.builder()
								.text("Four")
								.correct(true)
								.build(),
						AnswerDocument.builder()
								.text("Three")
								.correct(false)
								.build()))
				.furtherReadings(singletonList(FurtherReadingDocument.builder()
						.description("Basic Maths 101")
						.referenceLocation("http://basic.maths")
						.build()))
				.createdBy("webFederatedUserId of author")
				.createdDateTime(ZonedDateTime.parse("2018-05-09T08:12:43.456Z", ISO_ZONED_DATE_TIME))
				.updatedBy("webFederatedUserId of editor")
				.updatedDateTime(ZonedDateTime.parse("2018-05-20T18:02:12.042Z", ISO_ZONED_DATE_TIME))
				.status("APPROVED")
				.votes(10);
	}

	/**
	 * @return a Question Item Builder containing a valid question item, ready to have it's build method called
	 */
	public static QuestionItem.QuestionItemBuilder aSimpleQuestionItemAboutTriangles() {
		return QuestionItem.builder()
				.id("5678")
				.slug("triangle-sides-question")
				.summary("Triangle sides question")
				.questionText("How many sides does a triangle have?")
				.tags(asList("maths", "geometry"))
				.certifications(singletonList("Basic Maths"))
				.answers(asList(
						AnswerDocument.builder()
								.text("Three")
								.correct(true)
								.build(),
						AnswerDocument.builder()
								.text("Seven")
								.correct(false)
								.build()))
				.furtherReadings(singletonList(FurtherReadingDocument.builder()
						.description("Basic Maths 101")
						.referenceLocation("http://basic.maths")
						.build()))
				.createdBy("webFederatedUserId of author")
				.createdDateTime(ZonedDateTime.parse("2018-05-09T08:12:43.456Z", ISO_ZONED_DATE_TIME))
				.updatedBy("webFederatedUserId of editor")
				.updatedDateTime(ZonedDateTime.parse("2018-05-20T18:02:12.042Z", ISO_ZONED_DATE_TIME))
				.status("APPROVED")
				.votes(10);
	}

}