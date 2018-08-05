package uk.co.claritysoftware.exam.slammr.rest.user.web.config;

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

import java.time.ZonedDateTime;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import uk.co.claritysoftware.exam.slammr.rest.user.web.jackson.deserializer.ZonedDateTimeDeserializer;
import uk.co.claritysoftware.exam.slammr.rest.user.web.jackson.mixin.UserProfileMixin;
import uk.co.claritysoftware.exam.slammr.rest.user.web.jackson.mixin.UserRegistrationRequestMixin;
import uk.co.claritysoftware.exam.slammr.rest.user.web.model.UserProfile;
import uk.co.claritysoftware.exam.slammr.rest.user.web.model.UserRegistrationRequest;

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
