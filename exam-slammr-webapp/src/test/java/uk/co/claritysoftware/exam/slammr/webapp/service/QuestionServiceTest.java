package uk.co.claritysoftware.exam.slammr.webapp.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static uk.co.claritysoftware.exam.slammr.webapp.testsupport.persistence.dynamodb.item.question.QuestionItemTestDataFactory.aSimpleQuestionItemAboutSquares;
import static uk.co.claritysoftware.exam.slammr.webapp.testsupport.service.model.question.QuestionTestDataFactory.aSimpleQuestionAboutSquares;

import java.util.Optional;
import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.item.question.QuestionItem;
import uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.repository.DynamoDbQuestionItemRepository;
import uk.co.claritysoftware.exam.slammr.webapp.service.model.question.Question;

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
	public void shouldSaveNewQuestion() {
		// Given
		String id = UUID.randomUUID().toString();

		Question newQuestion = aSimpleQuestionAboutSquares()
				.id(null)
				.build();

		QuestionItem newQuestionItem = aSimpleQuestionItemAboutSquares()
				.id(null)
				.slug("square-sides-question")
				.build();

		Question savedQuestion = aSimpleQuestionAboutSquares()
				.id(id)
				.slug("square-sides-question")
				.build();

		QuestionItem savedQuestionItem = aSimpleQuestionItemAboutSquares()
				.id(id)
				.slug("square-sides-question")
				.build();

		given(questionItemRepository.save(any(QuestionItem.class)))
				.willReturn(savedQuestionItem);

		// When
		Question question = questionService.saveNewQuestion(newQuestion);

		// Then
		then(questionItemRepository).should().save(newQuestionItem);
		assertThat(question)
				.isEqualTo(savedQuestion);
	}

	@Test
	public void shouldFailToSaveNewQuestionGivenNullQuestion() {
		// Given
		Question newQuestion = null;

		// When
		Throwable throwable = catchThrowable(() -> questionService.saveNewQuestion(newQuestion));

		// Then
		then(questionItemRepository).shouldHaveZeroInteractions();
		assertThat(throwable)
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Cannot save a null Question");
	}

	@Test
	public void shouldFailToSaveNewQuestionGivenQuestionWithId() {
		// Given
		String id = UUID.randomUUID().toString();

		Question newQuestion = Question.builder()
				.id(id)
				.questionText("How many sides does a square have?")
				.build();

		// When
		Throwable throwable = catchThrowable(() -> questionService.saveNewQuestion(newQuestion));

		// Then
		then(questionItemRepository).shouldHaveZeroInteractions();
		assertThat(throwable)
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Cannot use this method to save an existing Question");
	}

	@Test
	public void shouldGetQuestionById() {
		// Given
		String id = "1234";

		QuestionItem questionItem = aSimpleQuestionItemAboutSquares()
				.id(id)
				.slug("square-sides-question")
				.build();

		Question expectedQuestion = aSimpleQuestionAboutSquares()
				.id(id)
				.slug("square-sides-question")
				.build();

		given(questionItemRepository.findOne(any()))
				.willReturn(questionItem);

		// When
		Optional<Question> question = questionService.getQuestionById(id);

		// Then
		then(questionItemRepository).should().findOne(id);
		assertThat(question)
				.as("Optional contains a value")
				.isPresent()
				.get()
				.as("Question is returned")
				.isEqualTo(expectedQuestion);
	}

	@Test
	public void shouldNotGetQuestionByIdGivenNonExistentId() {
		// Given
		String id = "9999";

		given(questionItemRepository.findOne(any()))
				.willReturn(null);

		// When
		Optional<Question> question = questionService.getQuestionById(id);

		// Then
		then(questionItemRepository).should().findOne(id);
		assertThat(question)
				.as("Optional is empty")
				.isNotPresent();
	}

	@Test
	public void shouldFailToGetQuestionByIdGivenNullId() {
		// Given
		String id = null;

		// When
		Throwable throwable = catchThrowable(() -> questionService.getQuestionById(id));

		// Then
		then(questionItemRepository).shouldHaveZeroInteractions();
		assertThat(throwable)
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Cannot retrieve a Question with null id");
	}
}