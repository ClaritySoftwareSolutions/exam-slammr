package uk.co.claritysoftware.exam.slammr.rest.question.web.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.co.claritysoftware.exam.slammr.rest.question.web.jackson.mixin.FurtherReadingMixin;
import uk.co.claritysoftware.exam.slammr.rest.question.web.jackson.mixin.EditableQuestionMixin;
import uk.co.claritysoftware.exam.slammr.rest.question.web.model.FurtherReading;
import uk.co.claritysoftware.exam.slammr.rest.question.web.model.EditableQuestion;
import uk.co.claritysoftware.exam.slammr.web.jackson.deserializer.ZonedDateTimeDeserializer;

import java.time.ZonedDateTime;

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

/**
 * Spring Boot configuration class for Jackson concerns
 */
@Configuration
public class JacksonConfiguration {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .configure(WRITE_DATES_AS_TIMESTAMPS, false)
                .addMixIn(EditableQuestion.class, EditableQuestionMixin.class)
                .addMixIn(FurtherReading.class, FurtherReadingMixin.class)
                .registerModules(new SimpleModule()
                        .addDeserializer(ZonedDateTime.class, new ZonedDateTimeDeserializer()));
    }
}
