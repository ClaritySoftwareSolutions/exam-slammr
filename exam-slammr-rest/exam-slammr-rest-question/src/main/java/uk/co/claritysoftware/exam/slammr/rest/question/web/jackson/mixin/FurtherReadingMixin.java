package uk.co.claritysoftware.exam.slammr.rest.question.web.jackson.mixin;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import uk.co.claritysoftware.exam.slammr.rest.question.web.model.FurtherReading;

/**
 * Jackson mixin to tell Jackson how to deserialize into a {@link FurtherReading}
 */
public abstract class FurtherReadingMixin {

    @JsonCreator
    public FurtherReadingMixin(
            @JsonProperty("description") String description,
            @JsonProperty("referenceLocation") String referenceLocation) {
    }
}