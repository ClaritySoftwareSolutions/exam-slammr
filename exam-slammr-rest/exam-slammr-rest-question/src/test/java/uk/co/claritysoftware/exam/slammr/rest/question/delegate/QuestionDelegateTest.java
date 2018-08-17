package uk.co.claritysoftware.exam.slammr.rest.question.delegate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.co.claritysoftware.exam.slammr.rest.question.exception.QuestionCreateException;
import uk.co.claritysoftware.exam.slammr.rest.question.factory.QuestionItemFactory;
import uk.co.claritysoftware.exam.slammr.rest.question.service.QuestionService;
import uk.co.claritysoftware.exam.slammr.rest.question.service.dynamodb.QuestionItem;
import uk.co.claritysoftware.exam.slammr.rest.question.web.model.EditableQuestion;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static uk.co.claritysoftware.exam.slammr.rest.question.service.dynamodb.QuestionStatus.UNAPPROVED;
import static uk.co.claritysoftware.exam.slammr.rest.question.testsupport.service.dynamodb.QuestionItemTestDataFactory.aSimpleQuestionAboutTriangles;
import static uk.co.claritysoftware.exam.slammr.rest.question.testsupport.web.model.EditableQuestionTestDataFactory.aSimpleQuestionCreateRequestAboutTriangles;

/**
 * Unit test class for {@link QuestionDelegate}
 */
@RunWith(MockitoJUnitRunner.class)
public class QuestionDelegateTest {

    @Mock
    private QuestionItemFactory questionItemFactory;

    @Mock
    private QuestionService questionService;

    @InjectMocks
    private QuestionDelegate questionDelegate;

    @Test
    public void shouldCreateQuestion() {
        // Given
        String identityId = "67890";
        EditableQuestion editableQuestion = aSimpleQuestionCreateRequestAboutTriangles().build();

        QuestionItem expectedNewQuestionItem = aSimpleQuestionAboutTriangles()
                .createdBy(identityId)
                .updatedBy(identityId)
                .status(UNAPPROVED)
                .votes(0)
                .build();
        given(questionItemFactory.unsavedQuestionItem(any(EditableQuestion.class), any(String.class)))
                .willReturn(expectedNewQuestionItem);

        String newQuestionId = UUID.randomUUID().toString();
        given(questionService.createQuestion(any(QuestionItem.class)))
                .willReturn(Optional.of(newQuestionId));

        // When
        String questionId = questionDelegate.createQuestion(editableQuestion, identityId);

        // Then
        then(questionItemFactory).should().unsavedQuestionItem(editableQuestion, identityId);
        then(questionService).should().createQuestion(expectedNewQuestionItem);
        assertThat(questionId)
                .as("Correct question id is returned")
                .isEqualTo(newQuestionId);
    }

    @Test
    public void shouldFailToRCreateQuestionGivenServiceReturnsEmpty() {
        // Given
        String identityId = "67890";
        EditableQuestion editableQuestion = aSimpleQuestionCreateRequestAboutTriangles().build();

        QuestionItem expectedNewQuestionItem = aSimpleQuestionAboutTriangles()
                .createdBy(identityId)
                .updatedBy(identityId)
                .status(UNAPPROVED)
                .votes(0)
                .build();
        given(questionItemFactory.unsavedQuestionItem(any(EditableQuestion.class), any(String.class)))
                .willReturn(expectedNewQuestionItem);

        given(questionService.createQuestion(any(QuestionItem.class)))
                .willReturn(Optional.empty());

        // When
        Throwable throwable = catchThrowable(() -> questionDelegate.createQuestion(editableQuestion, identityId));

        // Then
        then(questionItemFactory).should().unsavedQuestionItem(editableQuestion, identityId);
        then(questionService).should().createQuestion(expectedNewQuestionItem);
        assertThat(throwable)
                .as("QuestionCreateException was thrown")
                .isInstanceOf(QuestionCreateException.class)
                .hasMessage("Exception creating question 'How many sides does a triangle have?'");
    }
}