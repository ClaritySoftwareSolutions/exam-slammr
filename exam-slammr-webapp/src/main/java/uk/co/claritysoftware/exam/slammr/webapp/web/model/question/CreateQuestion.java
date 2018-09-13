package uk.co.claritysoftware.exam.slammr.webapp.web.model.question;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static uk.co.claritysoftware.exam.slammr.webapp.web.model.question.AnswerOption.generateEmptyAnswerOption;
import static uk.co.claritysoftware.exam.slammr.webapp.web.model.question.FurtherReading.generateEmptyFurtherReading;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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

	@Size(min = 2)
	private List<AnswerOption> answerOptions;

	private List<String> tags;

	private List<String> certifications;

	private List<FurtherReading> furtherReadings;

	/**
	 * @return a new CreateQuestion instance with empty/default fields
	 */
	public static CreateQuestion generateEmptyCreateQuestion() {
		return CreateQuestion.builder()
				.answerOptions(asList(
						generateEmptyAnswerOption(),
						generateEmptyAnswerOption()
				))
				.tags(singletonList(""))
				.certifications(singletonList(""))
                .furtherReadings(singletonList(generateEmptyFurtherReading()))
				.build();
	}

	public enum Action {
		save, addTag, addCertification, addFurtherReading, addAnswer
	}
}
