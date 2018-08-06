package uk.co.claritysoftware.exam.slammr.rest.question.service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

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

}