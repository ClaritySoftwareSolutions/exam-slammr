package uk.co.claritysoftware.exam.slammr.user.rest.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.co.claritysoftware.exam.slammr.user.rest.jackson.deserializer.ZonedDateTimeDeserializer;
import uk.co.claritysoftware.exam.slammr.user.rest.jackson.mixin.UserProfileMixin;
import uk.co.claritysoftware.exam.slammr.user.rest.jackson.mixin.UserRegistrationRequestMixin;
import uk.co.claritysoftware.exam.slammr.user.rest.model.UserProfile;
import uk.co.claritysoftware.exam.slammr.user.rest.model.UserRegistrationRequest;

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
                .addMixIn(UserProfile.class, UserProfileMixin.class)
                .addMixIn(UserRegistrationRequest.class, UserRegistrationRequestMixin.class)
                .registerModules(new SimpleModule()
                        .addDeserializer(ZonedDateTime.class, new ZonedDateTimeDeserializer()));
    }
}
