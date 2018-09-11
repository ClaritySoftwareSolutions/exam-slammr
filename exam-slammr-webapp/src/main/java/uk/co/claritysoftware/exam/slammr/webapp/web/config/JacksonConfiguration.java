package uk.co.claritysoftware.exam.slammr.webapp.web.config;

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import uk.co.claritysoftware.exam.slammr.webapp.web.jackson.mixin.SocialUserSignUpMixin;
import uk.co.claritysoftware.exam.slammr.webapp.web.model.SocialUserSignUp;

// import uk.co.claritysoftware.exam.slammr.web.jackson.deserializer.ZonedDateTimeDeserializer;

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
                .addMixIn(SocialUserSignUp.class, SocialUserSignUpMixin.class)
                .registerModules(new SimpleModule()
//                        .addDeserializer(ZonedDateTime.class, new ZonedDateTimeDeserializer())
				);
    }
}
