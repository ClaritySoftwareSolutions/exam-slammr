package uk.co.claritysoftware.exam.slammr.rest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.co.claritysoftware.exam.slammr.service.UserService;
import uk.co.claritysoftware.exam.slammr.service.aws.DynamoDbUserService;

/**
 * Configuration class for service beans
 */
@Configuration
public class ServiceConfig {

	@Bean
	public UserService userService() {
		return new DynamoDbUserService();
	}
}
