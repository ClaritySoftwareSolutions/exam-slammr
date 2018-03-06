package uk.co.claritysoftware.exam.slammr.lambda.question.factory;

import java.util.UUID;
import java.util.stream.Collectors;
import uk.co.claritysoftware.exam.slammr.lambda.question.dto.CreateQuestionRequest;
import uk.co.claritysoftware.exam.slammr.lambda.question.dynamodb.items.QuestionItem;

/**
 * @author Nathan Russell
 */
public class QuestionItemFactory {

	public static QuestionItem of(CreateQuestionRequest createQuestionRequest) {
		return QuestionItem.builder()
				.id(UUID.randomUUID().toString())
				.questionText(createQuestionRequest.getQuestionText())
				.answers(createQuestionRequest.getAnswers().stream()
						.map(answer -> AnswerDocumentFactory.from(answer))
						.collect(Collectors.toList()))
				.tags(createQuestionRequest.getTags())
				.certifications(createQuestionRequest.getCertifications())
				.furtherReadings(createQuestionRequest.getFurtherReadings().stream()
						.map(furtherReading -> FurtherReadingDocumentFactory.from(furtherReading))
						.collect(Collectors.toList()))
				.build();
	}
}
