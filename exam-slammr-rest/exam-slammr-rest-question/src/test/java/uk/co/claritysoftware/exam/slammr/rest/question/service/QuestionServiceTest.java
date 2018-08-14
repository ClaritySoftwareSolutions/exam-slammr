package uk.co.claritysoftware.exam.slammr.rest.question.service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.AmazonDynamoDBException;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.co.claritysoftware.exam.slammr.rest.question.service.dynamodb.QuestionItem;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static uk.co.claritysoftware.exam.slammr.rest.question.testsupport.service.dynamodb.QuestionItemTestDataFactory.aSimpleQuestionAboutSquares;

/**
 * Unit test class for {@link QuestionService}
 */
@Ignore
@RunWith(MockitoJUnitRunner.class)
public class QuestionServiceTest {

    @Mock
    private DynamoDBMapper dynamoDBMapper;

    @InjectMocks
    private QuestionService service;

    @Test
    public void shouldGetQuestion() {
        // Given
        String id = "1234";
        QuestionItem expectedQuestionItem = aSimpleQuestionAboutSquares().build();
        given(dynamoDBMapper.load(any(Class.class), any(String.class)))
                .willReturn(expectedQuestionItem);

        // When
        Optional<QuestionItem> userProfile = service.getQuestion(id);

        // Then
        then(dynamoDBMapper).should().load(QuestionItem.class, id);
        assertThat(userProfile)
                .as("Optional QuestionItem should be present")
                .isPresent()
                .get()
                .as("Returned QuestionItem is the expected QuestionItem")
                .isEqualTo(expectedQuestionItem);
    }

    @Test
    public void shouldNotGetQuestionGivenNonExistentId() {
        // Given
        String id = "1234";
        given(dynamoDBMapper.load(any(Class.class), any(String.class)))
                .willReturn(null);

        // When
        Optional<QuestionItem> userProfile = service.getQuestion(id);

        // Then
        then(dynamoDBMapper).should().load(QuestionItem.class, id);
        assertThat(userProfile)
                .as("Optional QuestionItem should not be present")
                .isNotPresent();
    }

    @Test
    public void shouldCreateQuestion() {
        // Given
        QuestionItem newQuestionItem = aSimpleQuestionAboutSquares()
                .id(null)
                .build();

        String questionId = "67890";
        doAnswer(invocationOnMock -> {
            newQuestionItem.setId(questionId);
            return null;
        }).when(dynamoDBMapper).save(any(QuestionItem.class));

        // When
        Optional<String> createdQuestionId = service.createQuestion(newQuestionItem);

        // Then
        then(dynamoDBMapper).should().save(newQuestionItem);
        assertThat(createdQuestionId)
                .as("Optional String should be present")
                .isPresent()
                .get()
                .as("Returned String is the expected question id")
                .isEqualTo(questionId);
    }

    @Test
    public void shouldNotCreateQuestionGivenDynamoThrowsException() {
        // Given
        QuestionItem newQuestionItem = aSimpleQuestionAboutSquares()
                .id(null)
                .build();
        doThrow(new AmazonDynamoDBException("Something bad happended"))
                .when(dynamoDBMapper).save(any(QuestionItem.class));

        // When
        Optional<String> createdUserIdentityId = service.createQuestion(newQuestionItem);

        // Then
        then(dynamoDBMapper).should().save(newQuestionItem);
        assertThat(createdUserIdentityId)
                .as("Optional String should not be present")
                .isNotPresent();
    }
}