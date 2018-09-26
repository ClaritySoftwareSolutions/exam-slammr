package uk.co.claritysoftware.exam.slammr.webapp.service;

import lombok.extern.slf4j.Slf4j;
import uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.item.user.UserProfileItem;
import uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.repository.DynamoDbUserProfileItemRepository;
import uk.co.claritysoftware.exam.slammr.webapp.service.model.user.ExamSlammrUserProfile;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;
import static uk.co.claritysoftware.exam.slammr.webapp.service.factory.ExamSlammrUserProfileFactory.valueOf;

/**
 * Service for User Profile related operations
 */
@Slf4j
public class UserProfileService {

    private final DynamoDbUserProfileItemRepository userProfileItemRepository;

    public UserProfileService(DynamoDbUserProfileItemRepository userProfileItemRepository) {
        this.userProfileItemRepository = userProfileItemRepository;
    }

    /**
     * Retrieve the User Profile identified by the specified user id. The User Profile is returned within an Optional
     *
     * @param userId the id of the User Profile to return
     * @return an Optional containing the User Profile, or an empty Optional if the User Profile could not be found by
     * the specified id
     */
    public Optional<ExamSlammrUserProfile> getUserProfileByUserId(String userId) {
        ExamSlammrUserProfile retrievedUserProfile = valueOf(userProfileItemRepository.findByCompositeUserId(userId));
        log.debug("Returning ExamSlammrUserProfile {}", retrievedUserProfile);
        return Optional.ofNullable(retrievedUserProfile);
    }

    /**
     * Saves a new User Profile, returning the newly saved User Profile as the persistence implementation will have updated
     * some of it's fields such as {@code id}
     *
     * @param userProfile the new ExamSlammrUserProfile to be saved
     * @return the saved ExamSlammrUserProfile
     */
    public ExamSlammrUserProfile saveNewUserProfile(ExamSlammrUserProfile userProfile) {
        checkArgument(userProfile != null, "Cannot save a null UserProfile");
        checkArgument(userProfile.getId() == null, "Cannot use this method to save an existing UserProfile");

        UserProfileItem newUserProfileItem = valueOf(userProfile);
        ExamSlammrUserProfile savedUserProfile = valueOf(userProfileItemRepository.save(newUserProfileItem));
        log.debug("Saved new ExamSlammrUserProfile {}", savedUserProfile);
        return savedUserProfile;
    }

}
