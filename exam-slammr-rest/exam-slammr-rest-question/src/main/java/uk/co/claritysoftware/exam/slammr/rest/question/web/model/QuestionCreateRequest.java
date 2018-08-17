package uk.co.claritysoftware.exam.slammr.rest.question.web.model;

import lombok.Builder;
import lombok.Value;
import uk.co.claritysoftware.exam.slammr.rest.question.web.contstraintvalidator.QuestionHasAnswers;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
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

    /**
     * A simple list of answers
     */
    @Size(min = 1)
    private final List<String> answers;

    /**
     * A set indicating which of the {@link QuestionCreateRequest#answers} are the correct answer(s) by their (1 based) index
     */
    @Size(min = 1)
    private final Set<Integer> correctAnswers;

    @Size(min = 1)
    private Set<String> tags;

    @Size(min = 1)
    private Set<String> certifications;

    @Valid
    private Set<FurtherReading> furtherReadings;
}
