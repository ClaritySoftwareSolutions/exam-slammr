package uk.co.claritysoftware.exam.slammr.lambda.question.factory;

import uk.co.claritysoftware.exam.slammr.lambda.question.dto.Answer;
import uk.co.claritysoftware.exam.slammr.lambda.question.dynamodb.items.AnswerDocument;

/**
 * @author Nathan Russell
 */
public class AnswerDocumentFactory {

	public static AnswerDocument from(Answer answer) {
		return AnswerDocument.builder()
				.text(answer.getText())
				.correct(answer.isCorrect())
				.build();
	}
}
