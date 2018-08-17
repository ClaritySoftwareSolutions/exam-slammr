package uk.co.claritysoftware.exam.slammr.rest.question.testsupport.web.model;

import uk.co.claritysoftware.exam.slammr.rest.question.web.model.EditableQuestion;
import uk.co.claritysoftware.exam.slammr.rest.question.web.model.FurtherReading;

import static com.google.common.collect.Sets.newHashSet;
import static java.util.Arrays.asList;

/**
 * Test data factory for {@link EditableQuestion} instances
 */
public class EditableQuestionTestDataFactory {

    /**
     * @return an Editable Builder containing a valid question, ready to have it's build method called
     */
    public static EditableQuestion.EditableQuestionBuilder aSimpleQuestionCreateRequestAboutSquares() {
        return EditableQuestion.builder()
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
     * @return an Editable Question Builder containing a valid question, ready to have it's build method called
     */
    public static EditableQuestion.EditableQuestionBuilder aSimpleQuestionCreateRequestAboutTriangles() {
        return EditableQuestion.builder()
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
