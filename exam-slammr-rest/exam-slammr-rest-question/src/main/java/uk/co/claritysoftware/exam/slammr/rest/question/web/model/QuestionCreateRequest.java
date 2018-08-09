package uk.co.claritysoftware.exam.slammr.rest.question.web.model;

import lombok.Builder;
import lombok.Value;
import uk.co.claritysoftware.exam.slammr.rest.question.web.contstraintvalidator.QuestionHasAnswers;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * Encapsulates the data for a new Question creation request
 */
@Value
@Builder
@QuestionHasAnswers
public final class QuestionCreateRequest implements Question {

    @NotBlank
    private final String questionText;

    @Size(min = 1)
    private final Set<String> answers;

    @Size(min = 1)
    private final Set<Integer> correctAnswers;

    @Size(min = 1)
    private Set<String> tags;

    @Size(min = 1)
    private Set<String> certifications;

    @Valid
    private Set<FurtherReading> furtherReadings;
}
