package uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.repository;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.item.UserProfileItem;

/**
 * Spring Data Spring Social repository for {@link UserProfileItem}
 */
@EnableScan
public interface DynamoDbUserProfileItemRepository extends CrudRepository<UserProfileItem, String> {

	/**
	 * Find and return the UserProfileItem with the specified compositeUserId
	 * @param compositeUserId the composite user id made up of the provider and the provider user id - eg: facebook:1234567890
	 * @return the matching UserProfileItem, or null
	 */
	UserProfileItem findByCompositeUserId(String compositeUserId);
}
