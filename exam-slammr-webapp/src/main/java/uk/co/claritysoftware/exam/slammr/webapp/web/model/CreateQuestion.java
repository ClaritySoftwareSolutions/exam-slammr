package uk.co.claritysoftware.exam.slammr.webapp.web.model;

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

	@NotEmpty
	private String summary;

	@NotEmpty
	private String question;

	private List<String> tags;

	private List<String> certifications;

	/**
	 * @return a new CreateQuestion instance with empty fields
	 */
	public static CreateQuestion generateEmptyCreateQuestion() {
		return CreateQuestion.builder()
				.tags(singletonList(""))
				.certifications(singletonList(""))
				.build();
	}

	public enum Action {
		save, addTag, addCertification
	}
}
