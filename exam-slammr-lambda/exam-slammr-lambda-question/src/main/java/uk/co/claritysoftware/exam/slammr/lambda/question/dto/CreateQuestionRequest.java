package uk.co.claritysoftware.exam.slammr.lambda.question.dto;

import java.util.List;

import lombok.Data;

/**
 * Pojo encapsulating the data for a Create Question request
 */
@Data
public class CreateQuestionRequest {

	private String questionText;

	private List<Answer> answers;

	private List<String> tags;

	private List<String> certifications;

	private List<FurtherReading> furtherReadings;

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
		validateAnswersHaveExactlyOneCorrectAnswer();
	}

	private void validateAnswersHaveExactlyOneCorrectAnswer() {
		long correctAnswers = answers.stream()
				.filter(answer -> answer.isCorrect())
				.count();
		if (correctAnswers != 1) {
			throw new IllegalStateException("Question should have exactly 1 answer marked as the correct answer, but it has " + correctAnswers);
		}
	}
}
