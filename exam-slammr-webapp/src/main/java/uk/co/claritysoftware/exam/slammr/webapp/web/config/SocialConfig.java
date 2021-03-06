package uk.co.claritysoftware.exam.slammr.webapp.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInController;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.connect.web.SignInAdapter;
import uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.repository.DynamoDbUserConnectionItemRepository;
import uk.co.claritysoftware.exam.slammr.webapp.security.springsocial.DynamoDbUsersConnectionRepository;

/**
 * Spring Social configuration class
 */
@Configuration
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter {

	@Bean
	public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator, UsersConnectionRepository usersConnectionRepository) {
		return new ProviderSignInUtils(connectionFactoryLocator, usersConnectionRepository);
	}

	@Bean
	public ProviderSignInController providerSignInController(ConnectionFactoryLocator connectionFactoryLocator, UsersConnectionRepository usersConnectionRepository, SignInAdapter signInAdapter) {
		return new ProviderSignInController(connectionFactoryLocator, usersConnectionRepository, signInAdapter);
	}

	@Bean
	public UsersConnectionRepository usersConnectionRepository(DynamoDbUserConnectionItemRepository userConnectionRepository, ConnectionFactoryLocator connectionFactoryLocator) {
		return new DynamoDbUsersConnectionRepository(userConnectionRepository, connectionFactoryLocator, noOpTextEncrypter());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

	@Bean
	public TextEncryptor noOpTextEncrypter() {
		return Encryptors.noOpText();
	}

}
