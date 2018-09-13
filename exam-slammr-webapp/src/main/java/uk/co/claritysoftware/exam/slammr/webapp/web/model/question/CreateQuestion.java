package uk.co.claritysoftware.exam.slammr.webapp.web.model.question;

import static java.util.Collections.singletonList;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class encapsulating fields within a request to create a new question
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class CreateQuestion {

	@NotNull
	private Action action;

	@NotEmpty(message = "{CreateQuestion.summary.NotEmpty.message}")
	private String summary;

	@NotEmpty(message = "{CreateQuestion.question.NotEmpty.message}")
	private String question;

	private List<String> tags;

	private List<String> certifications;

	private List<FurtherReading> furtherReadings;

	/**
	 * @return a new CreateQuestion instance with empty fields
	 */
	public static CreateQuestion generateEmptyCreateQuestion() {
		return CreateQuestion.builder()
				.tags(singletonList(""))
				.certifications(singletonList(""))
                .furtherReadings(singletonList(FurtherReading.builder().build()))
				.build();
	}

	public enum Action {
		save, addTag, addCertification, addFurtherReading
	}
}
