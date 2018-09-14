package uk.co.claritysoftware.exam.slammr.webapp.security.springsocial;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.item.user.UserConnectionItem;
import uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.repository.DynamoDbUserConnectionItemRepository;

/**
 * TODO - this should use a service rather than the repo directly
 * Implementation of {@link UsersConnectionRepository} that uses AWS DynamoDB as it's persistence store
 */
public class DynamoDbUsersConnectionRepository implements UsersConnectionRepository {

	private final DynamoDbUserConnectionItemRepository userConnectionRepository;

	private final ConnectionFactoryLocator connectionFactoryLocator;

	private final TextEncryptor textEncryptor;

	public DynamoDbUsersConnectionRepository(DynamoDbUserConnectionItemRepository userConnectionRepository, ConnectionFactoryLocator connectionFactoryLocator, TextEncryptor textEncryptor) {
		this.userConnectionRepository = userConnectionRepository;
		this.connectionFactoryLocator = connectionFactoryLocator;
		this.textEncryptor = textEncryptor;
	}

	@Override
	public List<String> findUserIdsWithConnection(Connection<?> connection) {
		ConnectionKey key = connection.getKey();
		List<UserConnectionItem> userConnectionItems = userConnectionRepository.findAllByProviderIdAndProviderUserId(key.getProviderId(), key.getProviderUserId());
		List<String> localUserIds = userConnectionItems.stream()
				.map(UserConnectionItem::getUserId)
				.collect(Collectors.toList());

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
		return localUserIds;
	}

	@Override
	public Set<String> findUserIdsConnectedTo(String providerId, Set<String> providerUserIds) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ConnectionRepository createConnectionRepository(String userId) {
		if (userId == null) {
			throw new IllegalArgumentException("userId cannot be null");
		}
		return new DynamoDBConnectionRepository(userId, userConnectionRepository, connectionFactoryLocator, textEncryptor);
	}
}
