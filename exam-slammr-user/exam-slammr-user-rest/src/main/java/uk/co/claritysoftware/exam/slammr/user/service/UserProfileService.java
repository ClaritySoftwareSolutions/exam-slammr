package uk.co.claritysoftware.exam.slammr.user.service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.claritysoftware.exam.slammr.user.rest.factory.UserProfileItemFactory;
import uk.co.claritysoftware.exam.slammr.user.rest.model.UserRegistrationRequest;
import uk.co.claritysoftware.exam.slammr.user.service.dynamodb.UserProfileItem;

import java.util.Optional;

/**
 * Service class exposing methods for User Profile concerns
 */
@Service
@Slf4j
public class UserProfileService {

    private final DynamoDBMapper dynamoDBMapper;

    private final UserProfileItemFactory userProfileItemFactory;

    @Autowired
    public UserProfileService(DynamoDBMapper dynamoDBMapper, UserProfileItemFactory userProfileItemFactory) {
        this.dynamoDBMapper = dynamoDBMapper;
        this.userProfileItemFactory = userProfileItemFactory;
    }

    /**
     * Return the {@link UserProfileItem} identified by the specified id, wrapped in an {@link Optional}
     *
     * @param identityId the identity id of the user whose profile should be returned
     * @return the an Optional containing the UserProfileItem, or empty if not found
     */
    public Optional<UserProfileItem> getUserProfile(String identityId) {
        log.debug("Get UserProfile with id {}", identityId);

        UserProfileItem userProfile = getUserProfileFromDynamoDb(identityId);
        if (userProfile == null) {
            log.info("UserProfileItem with webFederatedUserId HashKey {} not found", identityId);
        }
        return Optional.ofNullable(userProfile);
    }

    public UserProfileItem registerUserProfile(UserRegistrationRequest userRegistrationRequest, String identityId) {
        log.debug("Register UserProfile with request {}, identityId {}", userRegistrationRequest, identityId);

        UserProfileItem newUserProfile = userProfileItemFactory.valueOf(userRegistrationRequest, identityId);
        dynamoDBMapper.save(newUserProfile, new DynamoDBSaveExpression().withConditionalOperator());
        return null;

    }

    /**
     * Checks whether the {@link UserProfileItem} identified by the specified id already exists on the database
     *
     * @param identityId the identity id of the user
     * @return true if the User Profile exists
     */
    public boolean userProfileExists(String identityId) {
        return getUserProfileFromDynamoDb(identityId) != null;
    }

    private UserProfileItem getUserProfileFromDynamoDb(String identityId) {
        return dynamoDBMapper.load(UserProfileItem.class, identityId);
    }
}
