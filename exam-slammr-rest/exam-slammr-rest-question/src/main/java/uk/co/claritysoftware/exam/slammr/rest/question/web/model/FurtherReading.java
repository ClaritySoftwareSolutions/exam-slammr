package uk.co.claritysoftware.exam.slammr.rest.question.web.model;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;

/**
 * Encapsulates the data for a Further Reading reference to support a question and answer
 */
@Value
@Builder
public class FurtherReading {

    @NotBlank
    private String description;

    private String referenceLocation;

}
