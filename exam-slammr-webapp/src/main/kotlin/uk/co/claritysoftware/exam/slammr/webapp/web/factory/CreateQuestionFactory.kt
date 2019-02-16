package uk.co.claritysoftware.exam.slammr.webapp.web.factory

import uk.co.claritysoftware.exam.slammr.webapp.service.model.question.AnswerOption
import uk.co.claritysoftware.exam.slammr.webapp.service.model.question.FurtherReading
import uk.co.claritysoftware.exam.slammr.webapp.service.model.question.Question
import uk.co.claritysoftware.exam.slammr.webapp.service.model.question.QuestionStatus.SUBMITTED_FOR_APPROVAL
import uk.co.claritysoftware.exam.slammr.webapp.web.model.question.CreateQuestion
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.util.stream.Collectors

/**
 * Factory class to create instances to and from [CreateQuestion] instances
 */
class CreateQuestionFactory {

	companion object {

		@JvmStatic
		fun valueOf(createQuestion: CreateQuestion, authorId: String): Question {
			val now = ZonedDateTime.now().withZoneSameInstant(ZoneOffset.UTC)
			return Question(
					id = null,
					slug = null,
					summary = createQuestion.summary,
					questionText = createQuestion.question,
					answers = createQuestion.answerOptions.stream()
							.map { answerOption -> AnswerOption(text = answerOption.answer, isCorrect = answerOption.correct) }
							.collect(Collectors.toSet()),
					tags = createQuestion.tags.toSet(),
					certifications = createQuestion.certifications.toSet(),
					furtherReadings = createQuestion.furtherReadings.stream()
							.map { furtherReading -> FurtherReading(furtherReading.description, furtherReading.referenceLocation) }
							.collect(Collectors.toSet()),
					status = SUBMITTED_FOR_APPROVAL,
					votes = 0,
					createdBy = authorId,
					createdDateTime = now,
					updatedBy = null,
					updatedDateTime = null)
		}

	}
}

