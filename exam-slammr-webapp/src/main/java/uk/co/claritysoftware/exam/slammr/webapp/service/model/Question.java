package uk.co.claritysoftware.exam.slammr.webapp.service.model;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

import lombok.Builder;
import lombok.Value;

/**
 * Pojo defining an Exam Slammr Question
 */
@Value
@Builder
public class Question {

	private String id;

	private String questionText;

	private List<AnswerOption> answers;

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
