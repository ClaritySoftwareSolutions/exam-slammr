package uk.co.claritysoftware.exam.slammr.rest.question.web.model;

import java.util.List;
import java.util.Set;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import uk.co.claritysoftware.exam.slammr.rest.question.web.contstraintvalidator.QuestionHasAnswers;

import lombok.Builder;
import lombok.Value;

/**
 * Encapsulates the data to create or update a Question
 */
@Value
@Builder
@QuestionHasAnswers
public final class EditableQuestion {

    @NotBlank
    private String questionText;

    /**
     * A simple list of answers
     */
    @Size(min = 1)
    private List<String> answers;

    /**
     * A set indicating which of the {@link EditableQuestion#answers} are the correct answer(s) by their (1 based) index
     */
    @Size(min = 1)
    private Set<Integer> correctAnswers;

    @Size(min = 1)
    private Set<String> tags;

    @Size(min = 1)
    private Set<String> certifications;

    @Valid
    private Set<FurtherReading> furtherReadings;
}
