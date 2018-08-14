package uk.co.claritysoftware.exam.slammr.rest.user.service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.claritysoftware.exam.slammr.rest.user.service.dynamodb.UserProfileItem;

import java.util.Optional;

/**
 * Service class exposing methods for User Profile concerns
 */
@Service
@Slf4j
public class UserProfileService {

    private final DynamoDBMapper dynamoDBMapper;

    @Autowired
    public UserProfileService(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    /**
     * Return the {@link UserProfileItem} identified by the specified id, wrapped in an {@link Optional}
     *
     * @param identityId the identity id of the user whose profile should be returned
     * @return an Optional containing the UserProfileItem, or empty if not found
     */
    public Optional<UserProfileItem> getUserProfile(String identityId) {
        log.debug("Get UserProfile with webFederatedUserId HashKey {}", identityId);
        UserProfileItem userProfile = getUserProfileFromDynamoDb(identityId);
        if (userProfile == null) {
            log.info("UserProfileItem with webFederatedUserId HashKey {} not found", identityId);
        }
        return Optional.ofNullable(userProfile);
    }

    /**
     * Saves a new UserProfileItem record to dynamo
     *
     * @param newUserProfileItem the new user profile item to save
     * @return an Optional containing the identity id of the user, or empty if the new user profile could not be saved
     */
    public Optional<String> registerUserProfile(UserProfileItem newUserProfileItem) {
        log.debug("Register UserProfile with UserProfileItem {}", newUserProfileItem);

        try {
            dynamoDBMapper.save(newUserProfileItem,
                    new DynamoDBSaveExpression().withExpected(ImmutableMap.of("webFederatedUserId", new ExpectedAttributeValue(false)))
            );
            return Optional.of(newUserProfileItem.getWebFederatedUserId());

        } catch (ConditionalCheckFailedException e) {
            log.info("Attempt to register User Profile that already exists with webFederatedUserId HashKey {}", newUserProfileItem.getWebFederatedUserId());
            return Optional.empty();
        }
    }

    private UserProfileItem getUserProfileFromDynamoDb(String identityId) {
        return dynamoDBMapper.load(UserProfileItem.class, identityId);
    }
}
