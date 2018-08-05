package uk.co.claritysoftware.exam.slammr.rest.user.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.google.common.collect.ImmutableMap;
import uk.co.claritysoftware.exam.slammr.rest.user.web.exception.UserProfileAlreadyRegisteredException;
import uk.co.claritysoftware.exam.slammr.rest.user.web.factory.UserProfileItemFactory;
import uk.co.claritysoftware.exam.slammr.rest.user.web.model.UserRegistrationRequest;
import uk.co.claritysoftware.exam.slammr.rest.user.service.dynamodb.UserProfileItem;

import lombok.extern.slf4j.Slf4j;

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
        log.debug("Get UserProfile with webFederatedUserId HashKey {}", identityId);

        UserProfileItem userProfile = getUserProfileFromDynamoDb(identityId);
        if (userProfile == null) {
            log.info("UserProfileItem with webFederatedUserId HashKey {} not found", identityId);
        }
        return Optional.ofNullable(userProfile);
    }

    /**
     * Creates a new UserProfileItem record
     *
     * @param userRegistrationRequest the user registration request from the client containing the initial User Profile field values
     * @param identityId              the identity id of the user creating the new profile
     */
    public void registerUserProfile(UserRegistrationRequest userRegistrationRequest, String identityId) {
        log.debug("Register UserProfile with request {}, webFederatedUserId HashKey {}", userRegistrationRequest, identityId);

        UserProfileItem newUserProfile = userProfileItemFactory.valueOf(userRegistrationRequest, identityId);

        try {
            dynamoDBMapper.save(newUserProfile,
                    new DynamoDBSaveExpression().withExpected(ImmutableMap.of("webFederatedUserId", new ExpectedAttributeValue(false)))
            );
        } catch (ConditionalCheckFailedException e) {
            log.info("Attempt to register User Profile that already exists with webFederatedUserId HashKey {}", identityId);
            throw new UserProfileAlreadyRegisteredException(identityId);
        }
    }

    private UserProfileItem getUserProfileFromDynamoDb(String identityId) {
        return dynamoDBMapper.load(UserProfileItem.class, identityId);
    }
}
