package uk.co.claritysoftware.exam.slammr.rest.question.testsupport.web.model;

import uk.co.claritysoftware.exam.slammr.rest.question.web.model.FurtherReading;
import uk.co.claritysoftware.exam.slammr.rest.question.web.model.QuestionCreateRequest;

import static com.google.common.collect.Sets.newHashSet;
import static java.util.Arrays.asList;

/**
 * Test data factory for {@link QuestionCreateRequest} instances
 */
public class QuestionCreateRequestTestDataFactory {

    /**
     * @return a Question Create Request Builder containing a valid question, ready to have it's build method called
     */
    public static QuestionCreateRequest.QuestionCreateRequestBuilder aSimpleQuestionCreateRequestAboutSquares() {
        return QuestionCreateRequest.builder()
                .questionText("How many sides does a square have?")
                .tags(newHashSet("maths", "geometry"))
                .certifications(newHashSet("Basic Maths"))
                .answers(asList("Three", "Four"))
                .correctAnswers(newHashSet(2))
                .furtherReadings(newHashSet(FurtherReading.builder()
                        .description("Basic Maths 101")
                        .referenceLocation("http://basic.maths")
                        .build()));
    }

    /**
     * @return a Question Create Request Builder containing a valid question, ready to have it's build method called
     */
    public static QuestionCreateRequest.QuestionCreateRequestBuilder aSimpleQuestionCreateRequestAboutTriangles() {
        return QuestionCreateRequest.builder()
                .questionText("How many sides does a triangle have?")
                .tags(newHashSet("maths", "geometry"))
                .certifications(newHashSet("Basic Maths"))
                .answers(asList("Three", "Seven"))
                .correctAnswers(newHashSet(1))
                .furtherReadings(newHashSet(FurtherReading.builder()
                        .description("Basic Maths 101")
                        .referenceLocation("http://basic.maths")
                        .build()));
    }

}
