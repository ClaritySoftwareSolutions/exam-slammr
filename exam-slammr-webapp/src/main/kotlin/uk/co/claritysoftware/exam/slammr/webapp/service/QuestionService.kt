package uk.co.claritysoftware.exam.slammr.webapp.service

import com.github.slugify.Slugify
import com.google.common.base.Preconditions.checkArgument
import org.slf4j.LoggerFactory
import uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.item.question.QuestionItem
import uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.repository.DynamoDbQuestionItemRepository
import uk.co.claritysoftware.exam.slammr.webapp.service.factory.QuestionFactory
import uk.co.claritysoftware.exam.slammr.webapp.service.model.question.Question
import java.util.*

/**
 * Service for Question related operations
 */
open class QuestionService(val questionItemRepository: DynamoDbQuestionItemRepository) {

	private companion object {
		val log = LoggerFactory.getLogger(QuestionService::class.java)
	}

	/**
	 * Saves a new Question, returning the newly saved Question as the persistence implementation will have updated
	 * some of it's fields such as `id` and `status`
	 *
	 * @param question the new Question to be saved
	 * @return the saved Question
	 */
	open fun saveNewQuestion(question: Question): Question {
		checkArgument(question.id == null, "Cannot use this method to save an existing Question")

		val slug = generateSlug(question.summary)
		val newQuestionItem = QuestionFactory.valueOf(question, slug)
		val savedQuestion = QuestionFactory.valueOf(this.questionItemRepository.save(newQuestionItem))
		log.debug("Saved new Question {}", savedQuestion)
		return savedQuestion
	}

	/**
	 * Return the Question identified by the specified id wrapped in an Optional
	 *
	 * @param id the id of the Question
	 * @return the an Optional containing the retrieved Question, or an empty Optional
	 */
	open fun getQuestionById(id: String): Optional<Question> {
		val questionItem: QuestionItem? = questionItemRepository.findOne(id)
		return questionItem?.let {
			val question = QuestionFactory.valueOf(it)
			log.debug("Retrieved Question {}", question)
			Optional.of(question)
		}
				?: Optional.empty()

	}

	private fun generateSlug(questionSummary: String): String {
		val slugify = Slugify()
		return slugify.slugify(questionSummary)
	}

}