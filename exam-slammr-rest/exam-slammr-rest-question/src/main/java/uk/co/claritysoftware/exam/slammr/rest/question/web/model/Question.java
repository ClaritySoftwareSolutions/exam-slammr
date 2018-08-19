package uk.co.claritysoftware.exam.slammr.rest.question.web.model;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

import lombok.Builder;
import lombok.Value;

/**
 * Encapsulates the data to represent a Question
 */
@Value
@Builder
public class Question {

	private String id;

	private String questionText;

	private List<String> answers;

	private Set<Integer> correctAnswers;

	private Set<String> tags;

	private Set<String> certifications;

	private Set<FurtherReading> furtherReadings;

	private String createdBy;

	private ZonedDateTime createdDateTime;

	private String updatedBy;

	private ZonedDateTime updatedDateTime;

	private String status;

	private int votes;
}
