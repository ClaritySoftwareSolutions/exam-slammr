package uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.repository;

import java.util.List;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.item.UserConnectionItem;

/**
 * Spring Data Spring Social repository for {@link UserConnectionItem}
 */
@EnableScan
public interface DynamoDbUserConnectionItemRepository extends CrudRepository<UserConnectionItem, String> {

	List<UserConnectionItem> findAllByProviderIdAndProviderUserId(String providerId, String providerUserId);

	List<UserConnectionItem> findAllByProviderId(String providerId);

	UserConnectionItem findByUserIdAndProviderIdAndProviderUserId(String userId, String providerId, String providerUserId);
}
