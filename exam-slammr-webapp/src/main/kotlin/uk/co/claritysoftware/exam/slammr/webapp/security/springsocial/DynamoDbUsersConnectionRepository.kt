package uk.co.claritysoftware.exam.slammr.webapp.security.springsocial

import org.springframework.security.crypto.encrypt.TextEncryptor
import org.springframework.social.connect.Connection
import org.springframework.social.connect.ConnectionFactoryLocator
import org.springframework.social.connect.ConnectionRepository
import org.springframework.social.connect.UsersConnectionRepository
import uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.repository.DynamoDbUserConnectionItemRepository
import java.util.stream.Collectors

/**
 * TODO - this should use a service rather than the repo directly
 * Implementation of [UsersConnectionRepository] that uses AWS DynamoDB as it's persistence store
 */
class DynamoDbUsersConnectionRepository(val userConnectionRepository: DynamoDbUserConnectionItemRepository, val connectionFactoryLocator: ConnectionFactoryLocator, val textEncryptor: TextEncryptor) : UsersConnectionRepository {

	override fun findUserIdsWithConnection(connection: Connection<*>): List<String> {
		val key = connection.key
		val userConnectionItems = userConnectionRepository.findAllByProviderIdAndProviderUserId(key.providerId, key.providerUserId)

		/*
				   if (localUserIds.size() == 0 && connectionSignUp != null) {
					   String newUserId = connectionSignUp.execute(connection);
					   if (newUserId != null)
					   {
						   createConnectionRepository(newUserId).addConnection(connection);
						   return Arrays.asList(newUserId);
					   }
				   }
		   */
		return userConnectionItems.stream()
				.map { userConnectionItem -> userConnectionItem.userId!! }
				.collect(Collectors.toList())
	}

	override fun findUserIdsConnectedTo(providerId: String, providerUserIds: Set<String>): Set<String> {
		throw UnsupportedOperationException()
	}

	override fun createConnectionRepository(userId: String?): ConnectionRepository {
		if (userId == null) {
			throw IllegalArgumentException("userId cannot be null")
		}
		return DynamoDBConnectionRepository(userId, userConnectionRepository, connectionFactoryLocator, textEncryptor)
	}

}