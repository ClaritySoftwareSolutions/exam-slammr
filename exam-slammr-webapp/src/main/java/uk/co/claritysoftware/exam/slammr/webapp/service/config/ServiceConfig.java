package uk.co.claritysoftware.exam.slammr.webapp.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.repository.DynamoDbUserProfileItemRepository;
import uk.co.claritysoftware.exam.slammr.webapp.service.UserProfileService;

/**
 * Spring Configuration class for Service layer
 */
@Configuration
public class ServiceConfig {

	@Bean
	public UserProfileService userProfileServic(DynamoDbUserProfileItemRepository userProfileItemRepository) {
		return new UserProfileService(userProfileItemRepository);
	}
}
