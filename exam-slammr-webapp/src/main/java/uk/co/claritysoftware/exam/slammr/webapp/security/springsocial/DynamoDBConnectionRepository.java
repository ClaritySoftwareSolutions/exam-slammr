package uk.co.claritysoftware.exam.slammr.webapp.security.springsocial;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.DuplicateConnectionException;
import org.springframework.util.MultiValueMap;
import uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.item.UserConnectionItem;
import uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.repository.DynamoDbUserConnectionItemRepository;

/**
 * TODO - this should use a service rather than the repo directly
 * Implementation of {@link ConnectionRepository} that uses AWS DynamoDB as its persistence store
 */
public class DynamoDBConnectionRepository implements ConnectionRepository {

	private final String userId;

	private final DynamoDbUserConnectionItemRepository userConnectionRepository;

	private final ConnectionFactoryLocator connectionFactoryLocator;

	private final TextEncryptor textEncryptor;

	public DynamoDBConnectionRepository(String userId, DynamoDbUserConnectionItemRepository userConnectionRepository, ConnectionFactoryLocator connectionFactoryLocator, TextEncryptor textEncryptor) {
		this.userId = userId;
		this.userConnectionRepository = userConnectionRepository;
		this.connectionFactoryLocator = connectionFactoryLocator;
		this.textEncryptor = textEncryptor;
	}

	@Override
	public MultiValueMap<String, Connection<?>> findAllConnections() {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<Connection<?>> findConnections(String providerId) {
		List<UserConnectionItem> userConnectionItems = userConnectionRepository.findAllByProviderId(providerId);
		return userConnectionItems.stream()
				.map(this::createConnection)
				.collect(Collectors.toList());
	}

	@Override
	public <A> List<Connection<A>> findConnections(Class<A> apiType) {
		throw new UnsupportedOperationException();
	}

	@Override
	public MultiValueMap<String, Connection<?>> findConnectionsToUsers(MultiValueMap<String, String> providerUserIds) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Connection<?> getConnection(ConnectionKey connectionKey) {
		throw new UnsupportedOperationException();
	}

	@Override
	public <A> Connection<A> getConnection(Class<A> apiType, String providerUserId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public <A> Connection<A> getPrimaryConnection(Class<A> apiType) {
		throw new UnsupportedOperationException();
	}

	@Override
	public <A> Connection<A> findPrimaryConnection(Class<A> apiType) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addConnection(Connection<?> connection) {
		try {
			ConnectionData data = connection.createData();
			int rank = 1; // TODO - jdbcTemplate.queryForObject("select coalesce(max(rank) + 1, 1) as rank from " + tablePrefix + "UserConnection where userId = ? and providerId = ?", new Object[]{ userId, data.getProviderId() }, Integer.class);

			UserConnectionItem userConnectionItem = UserConnectionItem.builder()
					.userId(userId)
					.providerId(data.getProviderId())
					.providerUserId(data.getProviderUserId())
					.rank(rank)
					.displayName(data.getDisplayName())
					.profileUrl(data.getProfileUrl())
					.imageUrl(data.getImageUrl())
					.accessToken(encrypt(data.getAccessToken()))
					.secret(encrypt(data.getSecret()))
					.refreshToken(encrypt(data.getRefreshToken()))
					.expireTime(data.getExpireTime())
					.build();
			userConnectionRepository.save(userConnectionItem);
		} catch (DuplicateKeyException e) {
			throw new DuplicateConnectionException(connection.getKey());
		}

	}

	@Override
	public void updateConnection(Connection<?> connection) {
		ConnectionData data = connection.createData();
		UserConnectionItem userConnectionItem = userConnectionRepository.findByUserIdAndProviderIdAndProviderUserId(userId, data.getProviderId(), data.getProviderUserId());
		UserConnectionItem updatedUserConnectionItem = userConnectionItem.copy()
				.displayName(data.getDisplayName())
				.profileUrl(data.getProfileUrl())
				.imageUrl(data.getImageUrl())
				.accessToken(encrypt(data.getAccessToken()))
				.secret(encrypt(data.getSecret()))
				.refreshToken(encrypt(data.getRefreshToken()))
				.expireTime(data.getExpireTime())
				.build();
		userConnectionRepository.save(updatedUserConnectionItem);
	}

	@Override
	public void removeConnections(String providerId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeConnection(ConnectionKey connectionKey) {
		throw new UnsupportedOperationException();
	}

	private String encrypt(String text) {
		return text != null ? textEncryptor.encrypt(text) : text;
	}

	private String decrypt(String encryptedText) {
		return encryptedText != null ? textEncryptor.decrypt(encryptedText) : encryptedText;
	}

	private Long expireTime(long expireTime) {
		return expireTime == 0 ? null : expireTime;
	}

	private Connection<?> createConnection(UserConnectionItem userConnectionItem) {
		ConnectionData connectionData = new ConnectionData(
				userConnectionItem.getProviderId(), userConnectionItem.getProviderUserId(), userConnectionItem.getDisplayName(),
				userConnectionItem.getProfileUrl(), userConnectionItem.getImageUrl(), decrypt(userConnectionItem.getAccessToken()),
				decrypt(userConnectionItem.getSecret()), decrypt(userConnectionItem.getRefreshToken()), expireTime(userConnectionItem.getExpireTime()));
		ConnectionFactory<?> connectionFactory = connectionFactoryLocator.getConnectionFactory(connectionData.getProviderId());
		return connectionFactory.createConnection(connectionData);
	}

}
