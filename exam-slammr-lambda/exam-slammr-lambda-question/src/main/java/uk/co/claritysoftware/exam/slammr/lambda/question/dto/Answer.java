package uk.co.claritysoftware.exam.slammr.lambda.question.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Pojo describing an answer to a question
 */
@Data
public class Answer {

	private String text;

	@JsonProperty("isCorrect")
	private boolean correct;

}
