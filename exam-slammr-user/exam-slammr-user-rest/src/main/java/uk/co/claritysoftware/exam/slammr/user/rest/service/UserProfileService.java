package uk.co.claritysoftware.exam.slammr.user.rest.service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.claritysoftware.exam.slammr.user.rest.model.UserProfile;

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
     * Return the {@link UserProfile} identified by the specified id, wrapped in an {@link Optional}
     *
     * @param identityId the identity id of the user whose profile should be returned
     * @return the an Optional containing the UserProfile, or empty if not found
     */
    public Optional<UserProfile> getUserProfile(String identityId) {
        log.debug("Get UserProfile with id {}", identityId);

        UserProfile userProfile = dynamoDBMapper.load(UserProfile.class, identityId);
        if (userProfile == null) {
            log.info("UserProfile with id {} not found", identityId);
        }
        return Optional.ofNullable(userProfile);
    }

}
