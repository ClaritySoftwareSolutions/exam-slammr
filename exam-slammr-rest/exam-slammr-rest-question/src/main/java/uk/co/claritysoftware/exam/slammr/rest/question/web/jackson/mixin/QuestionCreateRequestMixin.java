package uk.co.claritysoftware.exam.slammr.rest.question.web.jackson.mixin;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import uk.co.claritysoftware.exam.slammr.rest.question.web.model.FurtherReading;
import uk.co.claritysoftware.exam.slammr.rest.question.web.model.QuestionCreateRequest;

import java.util.List;
import java.util.Set;

/**
 * Jackson mixin to tell Jackson how to deserialize into a {@link QuestionCreateRequest}
 */
public abstract class QuestionCreateRequestMixin {

    @JsonCreator
    public QuestionCreateRequestMixin(
            @JsonProperty("questionText") String questionText,
            @JsonProperty("answers") List<String> answers,
            @JsonProperty("correctAnswers") Set<Integer> correctAnswers,
            @JsonProperty("tags") Set<String> tags,
            @JsonProperty("certifications") Set<String> certifications,
            @JsonProperty("furtherReadings") Set<FurtherReading> furtherReadings) {
    }
}