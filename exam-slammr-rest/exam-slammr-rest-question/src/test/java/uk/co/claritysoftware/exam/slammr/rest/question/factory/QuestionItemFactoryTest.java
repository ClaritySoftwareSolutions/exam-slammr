package uk.co.claritysoftware.exam.slammr.rest.question.factory;

import org.junit.Test;
import uk.co.claritysoftware.exam.slammr.rest.question.service.dynamodb.QuestionItem;
import uk.co.claritysoftware.exam.slammr.rest.question.web.model.EditableQuestion;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.claritysoftware.exam.slammr.rest.question.service.dynamodb.QuestionStatus.UNAPPROVED;
import static uk.co.claritysoftware.exam.slammr.rest.question.testsupport.service.dynamodb.QuestionItemTestDataFactory.aSimpleQuestionAboutSquares;
import static uk.co.claritysoftware.exam.slammr.rest.question.testsupport.web.model.EditableQuestionTestDataFactory.aSimpleQuestionCreateRequestAboutSquares;

/**
 * Unit test class for {@link QuestionItemFactory}
 */
public class QuestionItemFactoryTest {

    private QuestionItemFactory questionItemFactory = new QuestionItemFactory();

    @Test
    public void shouldValueOf() {
        // Given
        EditableQuestion editableQuestion = aSimpleQuestionCreateRequestAboutSquares().build();
        String identityId = "1234";

        QuestionItem expectedQuestionItem = aSimpleQuestionAboutSquares()
                .id(null)
                .createdBy(identityId)
                .updatedBy(identityId)
                .status(UNAPPROVED)
                .votes(0)
                .build();

        // When
        QuestionItem questionItem = questionItemFactory.unsavedQuestionItem(editableQuestion, identityId);

        // Then
        assertThat(questionItem)
                .as("Equal QuestionItem except createdDateTimeField and updatedDateTime fields")
                .isEqualToIgnoringGivenFields(expectedQuestionItem,"createdDateTime", "updatedDateTime");
    }
}