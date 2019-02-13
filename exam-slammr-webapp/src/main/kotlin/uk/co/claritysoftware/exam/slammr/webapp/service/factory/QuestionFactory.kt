package uk.co.claritysoftware.exam.slammr.webapp.service.factory

import uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.item.question.AnswerDocument
import uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.item.question.FurtherReadingDocument
import uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.item.question.QuestionItem
import uk.co.claritysoftware.exam.slammr.webapp.service.model.question.AnswerOption
import uk.co.claritysoftware.exam.slammr.webapp.service.model.question.FurtherReading
import uk.co.claritysoftware.exam.slammr.webapp.service.model.question.Question
import uk.co.claritysoftware.exam.slammr.webapp.service.model.question.QuestionStatus
import java.util.stream.Collectors

/**
 * Factory class to create instances to and from {@link Question} instances
 */
class QuestionFactory {

	companion object {

		/**
		 * Creates a new [Question] from the specified [QuestionItem]
		 *
		 * @param questionItem the QuestionItem to create the Question from
		 * @return a populated Question instance
		 */
		@JvmStatic
		fun valueOf(questionItem: QuestionItem?): Question? {
			return if (questionItem != null)
				Question(
						id = questionItem.id,
						slug = questionItem.slug,
						summary = questionItem.summary,
						questionText = questionItem.questionText,
						answers = questionItem.answers.stream()
								.map { answerDocument -> AnswerOption(text = answerDocument.text, isCorrect = answerDocument.correct) }
								.collect(Collectors.toSet()),
						tags = questionItem.tags.toSet(),
						certifications = questionItem.certifications.toSet(),
						furtherReadings = questionItem.furtherReadings.stream()
								.map { (description, referenceLocation) -> FurtherReading(description = description, referenceLocation = referenceLocation) }
								.collect(Collectors.toSet()),
						status = QuestionStatus.valueOf(questionItem.status),
						votes = questionItem.votes,
						createdBy = questionItem.createdBy,
						createdDateTime = questionItem.createdDateTime,
						updatedBy = questionItem.updatedBy,
						updatedDateTime = questionItem.updatedDateTime)
			else
				null
		}

		/**
		 * Creates a new [QuestionItem] from the specified [Question]
		 *
		 * @param question the Question to create the QuestionItem from
		 * @param slug     the slug for this QuestionItem
		 * @return a populated QuestionItem instance
		 */
		@JvmStatic
		fun valueOf(question: Question?, slug: String): QuestionItem? {
			return if (question != null)
				QuestionItem(
						id = question.id,
						slug = slug,
						summary = question.summary,
						questionText = question.questionText,
						answers = question.answers.stream()
								.map { (text, isCorrect) ->
									AnswerDocument(
											text = text,
											correct = isCorrect)
								}
								.collect(Collectors.toList()),
						tags = question.tags.toList(),
						certifications = question.certifications.toList(),
						furtherReadings = question.furtherReadings.stream()
								.map { (description, referenceLocation) ->
									FurtherReadingDocument(
											description = description,
											referenceLocation = referenceLocation)
								}
								.collect(Collectors.toList()),
						status = question.status.name,
						votes = question.votes,
						createdBy = question.createdBy,
						createdDateTime = question.createdDateTime,
						updatedBy = question.updatedBy,
						updatedDateTime = question.updatedDateTime)
			else
				null
		}

	}
}


