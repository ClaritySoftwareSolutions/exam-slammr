package uk.co.claritysoftware.exam.slammr.webapp.web.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.encrypt.Encryptors
import org.springframework.security.crypto.encrypt.TextEncryptor
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.social.config.annotation.EnableSocial
import org.springframework.social.config.annotation.SocialConfigurerAdapter
import org.springframework.social.connect.ConnectionFactoryLocator
import org.springframework.social.connect.UsersConnectionRepository
import org.springframework.social.connect.web.ProviderSignInController
import org.springframework.social.connect.web.ProviderSignInUtils
import org.springframework.social.connect.web.SignInAdapter
import uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.repository.DynamoDbUserConnectionItemRepository
import uk.co.claritysoftware.exam.slammr.webapp.security.springsocial.DynamoDbUsersConnectionRepository

/**
 * Spring Social configuration class
 */
@Configuration
@EnableSocial
open class SocialConfig : SocialConfigurerAdapter() {

	@Bean
	open fun providerSignInUtils(connectionFactoryLocator: ConnectionFactoryLocator, usersConnectionRepository: UsersConnectionRepository): ProviderSignInUtils {
		return ProviderSignInUtils(connectionFactoryLocator, usersConnectionRepository)
	}

	@Bean
	open fun providerSignInController(connectionFactoryLocator: ConnectionFactoryLocator, usersConnectionRepository: UsersConnectionRepository, signInAdapter: SignInAdapter): ProviderSignInController {
		return ProviderSignInController(connectionFactoryLocator, usersConnectionRepository, signInAdapter)
	}

	@Bean
	open fun usersConnectionRepository(userConnectionRepository: DynamoDbUserConnectionItemRepository, connectionFactoryLocator: ConnectionFactoryLocator): UsersConnectionRepository {
		return DynamoDbUsersConnectionRepository(userConnectionRepository, connectionFactoryLocator, noOpTextEncrypter())
	}

	@Bean
	open fun passwordEncoder(): PasswordEncoder = NoOpPasswordEncoder.getInstance()

	@Bean
	open fun noOpTextEncrypter(): TextEncryptor = Encryptors.noOpText()

}
