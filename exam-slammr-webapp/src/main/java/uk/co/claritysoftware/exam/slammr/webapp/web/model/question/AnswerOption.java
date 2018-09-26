package uk.co.claritysoftware.exam.slammr.webapp.web.model.question;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * Encapsulates the data for a possible answer of a question
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class AnswerOption {

    @NotBlank
    private String answer;

    private boolean correct;

    /**
     * @return a new AnswerOption instance with empty fields
     */
    public static AnswerOption generateEmptyAnswerOption() {
        return AnswerOption.builder().build();
    }
}
