package uk.co.claritysoftware.exam.slammr.webapp.service;

import static com.google.common.base.Preconditions.checkArgument;
import static uk.co.claritysoftware.exam.slammr.webapp.service.factory.QuestionFactory.valueOf;

import java.util.Optional;
import com.github.slugify.Slugify;
import uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.item.question.QuestionItem;
import uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.repository.DynamoDbQuestionItemRepository;
import uk.co.claritysoftware.exam.slammr.webapp.service.model.question.Question;

import lombok.extern.slf4j.Slf4j;

/**
 * Service for Question related operations
 */
@Slf4j
public class QuestionService {

	private final DynamoDbQuestionItemRepository questionItemRepository;

	public QuestionService(DynamoDbQuestionItemRepository questionItemRepository) {
		this.questionItemRepository = questionItemRepository;
	}

	/**
	 * Saves a new Question, returning the newly saved Question as the persistence implementation will have updated
	 * some of it's fields such as {@code id} and {@code status}
	 *
	 * @param question the new Question to be saved
	 * @return the saved Question
	 */
	public Question saveNewQuestion(Question question) {
		checkArgument(question != null, "Cannot save a null Question");
		checkArgument(question.getId() == null, "Cannot use this method to save an existing Question");

		String slug = generateSlug(question.getSummary());
		QuestionItem newQuestionItem = valueOf(question, slug);
		Question savedQuestion = valueOf(questionItemRepository.save(newQuestionItem));
		log.debug("Saved new Question {}", savedQuestion);
		return savedQuestion;
	}

	/**
	 * Return the Question identified by the specified id wrapped in an Optional
	 *
	 * @param id the id of the Question
	 * @return the an Optional containing the retrieved Question, or an empty Optional
	 */
	public Optional<Question> getQuestionById(String id) {
		checkArgument(id != null, "Cannot retrieve a Question with null id");

		Question question = valueOf(questionItemRepository.findOne(id));
		log.debug("Retrieved Question {}", question);
		return Optional.ofNullable(question);
	}

	private String generateSlug(String questionSummary) {
		Slugify slugify = new Slugify();
		String slug = slugify.slugify(questionSummary);
		return slug;
	}

}
