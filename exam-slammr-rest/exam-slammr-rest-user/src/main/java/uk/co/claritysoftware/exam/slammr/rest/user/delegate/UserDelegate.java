package uk.co.claritysoftware.exam.slammr.rest.user.delegate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.co.claritysoftware.exam.slammr.rest.user.exception.UserProfileAlreadyRegisteredException;
import uk.co.claritysoftware.exam.slammr.rest.user.exception.UserProfileNotFoundException;
import uk.co.claritysoftware.exam.slammr.rest.user.factory.UserProfileFactory;
import uk.co.claritysoftware.exam.slammr.rest.user.factory.UserProfileItemFactory;
import uk.co.claritysoftware.exam.slammr.rest.user.service.UserProfileService;
import uk.co.claritysoftware.exam.slammr.rest.user.service.dynamodb.UserProfileItem;
import uk.co.claritysoftware.exam.slammr.rest.user.web.model.UserProfile;
import uk.co.claritysoftware.exam.slammr.rest.user.web.model.UserRegistrationRequest;

import java.util.Optional;

/**
 * Delegate class for User functionality
 */
@Slf4j
@Component
public class UserDelegate {

    private final UserProfileService userProfileService;

    private final UserProfileFactory userProfileFactory;

    private final UserProfileItemFactory userProfileItemFactory;

    @Autowired
    public UserDelegate(UserProfileService userProfileService, UserProfileFactory userProfileFactory, UserProfileItemFactory userProfileItemFactory) {
        this.userProfileService = userProfileService;
        this.userProfileFactory = userProfileFactory;
        this.userProfileItemFactory = userProfileItemFactory;
    }

    /**
     * Return the {@link UserProfile} identified by the specified id
     *
     * @param identityId the identity id of the user whose profile should be returned
     * @return the UserProfile
     * @throws UserProfileNotFoundException if the user profile could not be found
     */
    public UserProfile getUserProfile(String identityId) {
        log.debug("Get UserProfile with webFederatedUserId HashKey {}", identityId);
        return userProfileFactory.valueOf(userProfileService.getUserProfile(identityId)
                .orElseThrow(() -> new UserProfileNotFoundException(identityId)));
    }

    /**
     * Creates a new User Profile
     *
     * @param userRegistrationRequest the user registration request from the client containing the initial User Profile field values
     * @param identityId              the identity id of the user creating the new profile
     * @throws UserProfileAlreadyRegisteredException if a new user profile is not created by the service
     */
    public void registerUserProfile(UserRegistrationRequest userRegistrationRequest, String identityId) {
        log.debug("Register UserProfile with request {}, webFederatedUserId HashKey {}", userRegistrationRequest, identityId);

        UserProfileItem newUserProfile = userProfileItemFactory.valueOf(userRegistrationRequest, identityId);
        Optional<String> createdUserId = userProfileService.registerUserProfile(newUserProfile);
        if (!createdUserId.isPresent()) {
            log.info("Attempt to register User Profile that already exists with webFederatedUserId HashKey {}", identityId);
            throw new UserProfileAlreadyRegisteredException(identityId);
        }
    }
}
