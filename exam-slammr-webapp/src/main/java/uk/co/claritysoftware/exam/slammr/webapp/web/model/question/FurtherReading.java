package uk.co.claritysoftware.exam.slammr.webapp.web.model.question;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.validation.constraints.NotBlank;

/**
 * Encapsulates the data for a Further Reading reference to support a question and answer
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class FurtherReading {

    @NotBlank
    private String description;

    private String referenceLocation;

}
