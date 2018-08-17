package uk.co.claritysoftware.exam.slammr.rest.question.testsupport.service.dynamodb;

import uk.co.claritysoftware.exam.slammr.rest.question.service.dynamodb.AnswerDocument;
import uk.co.claritysoftware.exam.slammr.rest.question.service.dynamodb.FurtherReadingDocument;
import uk.co.claritysoftware.exam.slammr.rest.question.service.dynamodb.QuestionItem;

import java.time.ZonedDateTime;

import static com.google.common.collect.Sets.newHashSet;
import static java.time.format.DateTimeFormatter.ISO_ZONED_DATE_TIME;
import static java.util.Arrays.asList;
import static uk.co.claritysoftware.exam.slammr.rest.question.service.dynamodb.QuestionStatus.APPROVED;

/**
 * Test data factory for {@link QuestionItem} instances
 */
public class QuestionItemTestDataFactory {

    /**
     * @return a Question Item Builder containing a valid question item, ready to have it's build method called
     */
    public static QuestionItem.QuestionItemBuilder aSimpleQuestionAboutSquares() {
        return QuestionItem.builder()
                .id("1234")
                .questionText("How many sides does a square have?")
                .tags(newHashSet("maths", "geometry"))
                .certifications(newHashSet("Basic Maths"))
                .answers(asList(AnswerDocument.builder()
                                .text("Three")
                                .correct(false)
                                .build(),
                        AnswerDocument.builder()
                                .text("Four")
                                .correct(true)
                                .build()))
                .furtherReadings(newHashSet(FurtherReadingDocument.builder()
                        .description("Basic Maths 101")
                        .referenceLocation("http://basic.maths")
                        .build()))
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
    public static QuestionItem.QuestionItemBuilder aSimpleQuestionAboutTriangles() {
        return QuestionItem.builder()
                .id("5678")
                .questionText("How many sides does a triangle have?")
                .tags(newHashSet("maths", "geometry"))
                .certifications(newHashSet("Basic Maths"))
                .answers(asList(AnswerDocument.builder()
                                .text("Three")
                                .correct(true)
                                .build(),
                        AnswerDocument.builder()
                                .text("Seven")
                                .correct(false)
                                .build()))
                .furtherReadings(newHashSet(FurtherReadingDocument.builder()
                        .description("Basic Maths 101")
                        .referenceLocation("http://basic.maths")
                        .build()))
                .createdBy("webFederatedUserId of author")
                .createdDateTime(ZonedDateTime.parse("2018-05-09T08:12:43.456Z", ISO_ZONED_DATE_TIME))
                .updatedBy("webFederatedUserId of editor")
                .updatedDateTime(ZonedDateTime.parse("2018-05-20T18:02:12.042Z", ISO_ZONED_DATE_TIME))
                .status(APPROVED)
                .votes(10);
    }

}