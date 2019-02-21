package uk.co.claritysoftware.exam.slammr.webapp.security.springsocial

import org.springframework.dao.DuplicateKeyException
import org.springframework.security.crypto.encrypt.TextEncryptor
import org.springframework.social.connect.*
import org.springframework.util.MultiValueMap
import uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.item.user.UserConnectionItem
import uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.repository.DynamoDbUserConnectionItemRepository
import java.time.Instant
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.util.stream.Collectors

/**
 * TODO - this should use a service rather than the repo directly
 * Implementation of [ConnectionRepository] that uses AWS DynamoDB as its persistence store
 */
class DynamoDBConnectionRepository(val userId: String, val userConnectionRepository: DynamoDbUserConnectionItemRepository, val connectionFactoryLocator: ConnectionFactoryLocator, val textEncryptor: TextEncryptor) : ConnectionRepository {

	override fun findAllConnections(): MultiValueMap<String, Connection<*>> {
		throw UnsupportedOperationException()
	}

	override fun findConnections(providerId: String): List<Connection<*>> {
		val userConnectionItems = userConnectionRepository.findAllByProviderId(providerId)
		return userConnectionItems.stream()
				.map { userConnectionItem -> this.createConnection(userConnectionItem) }
				.collect(Collectors.toList())
	}

	override fun <A> findConnections(apiType: Class<A>): List<Connection<A>> {
		throw UnsupportedOperationException()
	}

	override fun findConnectionsToUsers(providerUserIds: MultiValueMap<String, String>): MultiValueMap<String, Connection<*>> {
		throw UnsupportedOperationException()
	}

	override fun getConnection(connectionKey: ConnectionKey): Connection<*> {
		throw UnsupportedOperationException()
	}

	override fun <A> getConnection(apiType: Class<A>, providerUserId: String): Connection<A> {
		throw UnsupportedOperationException()
	}

	override fun <A> getPrimaryConnection(apiType: Class<A>): Connection<A> {
		throw UnsupportedOperationException()
	}

	override fun <A> findPrimaryConnection(apiType: Class<A>): Connection<A> {
		throw UnsupportedOperationException()
	}

	override fun addConnection(connection: Connection<*>) {
		try {
			val data = connection.createData()
			val rank = 1 // TODO - jdbcTemplate.queryForObject("select coalesce(max(rank) + 1, 1) as rank from " + tablePrefix + "UserConnection where userId = ? and providerId = ?", new Object[]{ userId, data.getProviderId() }, Integer.class);

			val userConnectionItem = UserConnectionItem(
					userId = userId,
					providerId = data.providerId,
					providerUserId = data.providerUserId,
					rank = rank,
					displayName = data.displayName,
					profileUrl = data.profileUrl,
					imageUrl = data.imageUrl,
					accessToken = encrypt(data.accessToken),
					secret = encrypt(data.secret),
					refreshToken = encrypt(data.refreshToken),
					expireTime = toZonedDateTime(data.expireTime))
			userConnectionRepository.save(userConnectionItem)
		} catch (e: DuplicateKeyException) {
			throw DuplicateConnectionException(connection.key)
		}

	}

	override fun updateConnection(connection: Connection<*>) {
		val data = connection.createData()
		val userConnectionItem = userConnectionRepository.findByUserIdAndProviderIdAndProviderUserId(userId, data.providerId, data.providerUserId)
		val updatedUserConnectionItem = UserConnectionItem(
				userId = userConnectionItem.userId,
				providerId = userConnectionItem.providerId,
				providerUserId = userConnectionItem.providerUserId,
				rank = userConnectionItem.rank,
				displayName = data.displayName,
				profileUrl = data.profileUrl,
				imageUrl = data.imageUrl,
				accessToken = encrypt(data.accessToken),
				secret = encrypt(data.secret),
				refreshToken = encrypt(data.refreshToken),
				expireTime = toZonedDateTime(data.expireTime))
		userConnectionRepository.save(updatedUserConnectionItem)
	}

	override fun removeConnections(providerId: String) {
		throw UnsupportedOperationException()
	}

	override fun removeConnection(connectionKey: ConnectionKey) {
		throw UnsupportedOperationException()
	}

	private fun encrypt(text: String?) = text?.let { textEncryptor.encrypt(it) }

	private fun decrypt(encryptedText: String?) = encryptedText?.let { textEncryptor.decrypt(it) }

	private fun toZonedDateTime(millisSinceEpoch: Long?): ZonedDateTime {
		var millisSinceEpoch = millisSinceEpoch
		if (millisSinceEpoch == null) {
			millisSinceEpoch = 0L
		}
		return ZonedDateTime.ofInstant(Instant.ofEpochMilli(millisSinceEpoch), ZoneOffset.UTC)
	}

	private fun toMillis(expireTime: ZonedDateTime?): Long? {
		return expireTime?.toInstant()?.toEpochMilli()
	}

	private fun createConnection(userConnectionItem: UserConnectionItem): Connection<*> {
		val connectionData = ConnectionData(
				userConnectionItem.providerId,
				userConnectionItem.providerUserId,
				userConnectionItem.displayName,
				userConnectionItem.profileUrl,
				userConnectionItem.imageUrl,
				decrypt(userConnectionItem.accessToken),
				decrypt(userConnectionItem.secret),
				decrypt(userConnectionItem.refreshToken),
				toMillis(userConnectionItem.expireTime))
		val connectionFactory = connectionFactoryLocator.getConnectionFactory(connectionData.providerId)
		return connectionFactory.createConnection(connectionData)
	}

}