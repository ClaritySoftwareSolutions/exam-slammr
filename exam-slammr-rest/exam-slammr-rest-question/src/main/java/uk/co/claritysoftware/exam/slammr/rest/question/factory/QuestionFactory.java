package uk.co.claritysoftware.exam.slammr.rest.question.factory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import uk.co.claritysoftware.exam.slammr.rest.question.service.dynamodb.AnswerDocument;
import uk.co.claritysoftware.exam.slammr.rest.question.service.dynamodb.QuestionItem;
import uk.co.claritysoftware.exam.slammr.rest.question.web.model.FurtherReading;
import uk.co.claritysoftware.exam.slammr.rest.question.web.model.Question;

/**
 * Factory class to create {@link Question} instances
 */
@Component
public class QuestionFactory {

	public Question valueOf(QuestionItem questionItem) {
		return Question.builder()
				.id(questionItem.getId())
				.questionText(questionItem.getQuestionText())
				.answers(answers(questionItem))
				.correctAnswers(correctAnswers(questionItem))
				.furtherReadings(furtherReadings(questionItem))
				.tags(questionItem.getTags())
				.certifications(questionItem.getCertifications())
				.createdBy(questionItem.getCreatedBy())
				.createdDateTime(questionItem.getCreatedDateTime())
				.updatedBy(questionItem.getUpdatedBy())
				.updatedDateTime(questionItem.getUpdatedDateTime())
				.status(questionItem.getStatus().name())
				.votes(questionItem.getVotes())
				.build();
	}

	private static List<String> answers(QuestionItem questionItem) {
		return questionItem.getAnswers().stream()
				.map(answerDocument -> answerDocument.getText())
				.collect(Collectors.toList());
	}

	private static Set<Integer> correctAnswers(QuestionItem questionItem) {
		int index = 1;
		Set<Integer> correctAnswers = new HashSet<>();
		for (AnswerDocument answerDocument : questionItem.getAnswers()) {
			if (answerDocument.isCorrect()) {
				correctAnswers.add(index);
			}
			index++;
		}
		return correctAnswers;
	}

	private static Set<FurtherReading> furtherReadings(QuestionItem questionItem) {
		return questionItem.getFurtherReadings().stream()
				.map(furtherReadingDocument -> FurtherReading.builder()
						.description(furtherReadingDocument.getDescription())
						.referenceLocation(furtherReadingDocument.getReferenceLocation())
						.build())
				.collect(Collectors.toSet());
	}
}
