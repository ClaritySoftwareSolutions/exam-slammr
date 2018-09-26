package uk.co.claritysoftware.exam.slammr.webapp.testsupport.web.model.question;

import uk.co.claritysoftware.exam.slammr.webapp.web.model.question.AnswerOption;
import uk.co.claritysoftware.exam.slammr.webapp.web.model.question.CreateQuestion;
import uk.co.claritysoftware.exam.slammr.webapp.web.model.question.FurtherReading;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static uk.co.claritysoftware.exam.slammr.webapp.web.model.question.CreateQuestion.Action.save;

/**
 * Test data factory for {@link CreateQuestion} instances
 */
public class CreateQuestionTestDataFactory {

    /**
     * @return a CreateQuestion Builder containing a valid question, ready to have it's build method called
     */
    public static CreateQuestion.CreateQuestionBuilder aSimpleCreateQuestionAboutTriangles() {
        return CreateQuestion.builder()
                .summary("Triangle sides question")
                .question("How many sides does a triangle have?")
                .answerOptions(asList(AnswerOption.builder()
                                .answer("Three")
                                .correct(true)
                                .build(),
                        AnswerOption.builder()
                                .answer("Seven")
                                .correct(false)
                                .build()))
                .furtherReadings(singletonList(FurtherReading.builder()
                        .description("Basic Maths 101")
                        .referenceLocation("http://basic.maths")
                        .build()))
                .tags(asList("maths", "geometry"))
                .certifications(singletonList("Basic Maths"))
                .action(save);
    }

}
