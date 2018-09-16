package uk.co.claritysoftware.exam.slammr.webapp.service;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.repository.DynamoDbQuestionItemRepository;

/**
 * Unit test class  for {@link QuestionService}
 */
@RunWith(MockitoJUnitRunner.class)
public class QuestionServiceTest {

	@Mock
	private DynamoDbQuestionItemRepository questionItemRepository;

	@InjectMocks
	private QuestionService questionService;

	@Test
	@Ignore
	public void should() {
		// Given

		// When

		// Then
	}
}