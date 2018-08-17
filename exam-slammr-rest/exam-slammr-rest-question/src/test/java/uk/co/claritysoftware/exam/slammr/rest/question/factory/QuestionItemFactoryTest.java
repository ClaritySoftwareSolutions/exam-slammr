package uk.co.claritysoftware.exam.slammr.rest.question.factory;

import org.junit.Test;
import uk.co.claritysoftware.exam.slammr.rest.question.service.dynamodb.QuestionItem;
import uk.co.claritysoftware.exam.slammr.rest.question.web.model.QuestionCreateRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.claritysoftware.exam.slammr.rest.question.testsupport.service.dynamodb.QuestionItemTestDataFactory.aSimpleQuestionAboutSquares;
import static uk.co.claritysoftware.exam.slammr.rest.question.testsupport.web.model.QuestionCreateRequestTestDataFactory.aSimpleQuestionCreateRequestAboutSquares;

/**
 * Unit test class for {@link QuestionItemFactory}
 */
public class QuestionItemFactoryTest {

    private QuestionItemFactory questionItemFactory = new QuestionItemFactory();

    @Test
    public void shouldValueOf() {
        // Given
        QuestionCreateRequest questionCreateRequest = aSimpleQuestionCreateRequestAboutSquares().build();
        String identityId = "1234";

        QuestionItem expectedQuestionItem = aSimpleQuestionAboutSquares()
                .id(null)
                .createdBy(identityId)
                .build();

        // When
        QuestionItem questionItem = questionItemFactory.valueOf(questionCreateRequest, identityId);

        // Then
        assertThat(questionItem)
                .as("Equals expected QuestionItem except createdDateTimeField")
                .isEqualToIgnoringGivenFields(expectedQuestionItem,"createdDateTime");
    }
}