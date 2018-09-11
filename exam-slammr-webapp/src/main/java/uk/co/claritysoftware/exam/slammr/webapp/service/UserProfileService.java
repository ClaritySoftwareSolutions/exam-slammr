package uk.co.claritysoftware.exam.slammr.webapp.service;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.Optional;
import uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.item.UserProfileItem;
import uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.repository.DynamoDbUserProfileItemRepository;
import uk.co.claritysoftware.exam.slammr.webapp.service.model.ExamSlammrUserProfile;

import lombok.extern.slf4j.Slf4j;

/**
 * Service for User Profile related operations
 */
@Slf4j
public class UserProfileService {

	private final DynamoDbUserProfileItemRepository userProfileItemRepository;

	public UserProfileService(DynamoDbUserProfileItemRepository userProfileItemRepository) {
		this.userProfileItemRepository = userProfileItemRepository;
	}

	public Optional<ExamSlammrUserProfile> getUserProfileByUserId(String userId) {
		ExamSlammrUserProfile retrievedUserProfile = valueOf(userProfileItemRepository.findByCompositeUserId(userId));
		log.debug("Returning ExamSlammrUserProfile {}", retrievedUserProfile);
		return Optional.ofNullable(retrievedUserProfile);
	}

	public ExamSlammrUserProfile saveNewUserProfile(ExamSlammrUserProfile userProfile) {
		checkArgument(userProfile != null, "Cannot save a null UserProfile");
		checkArgument(userProfile.getId() == null, "Cannot use this method to save an existing UserProfile");

		UserProfileItem newUserProfileItem = valueOf(userProfile);
		ExamSlammrUserProfile savedUserProfile = valueOf(userProfileItemRepository.save(newUserProfileItem));
		log.debug("Saved new ExamSlammrUserProfile {}", savedUserProfile);
		return savedUserProfile;
	}

	private ExamSlammrUserProfile valueOf(UserProfileItem userProfileItem) {
		return userProfileItem != null ? ExamSlammrUserProfile.builder()
				.id(userProfileItem.getId())
				.compositeUserId(userProfileItem.getCompositeUserId())
				.firstname(userProfileItem.getFirstname())
				.surname(userProfileItem.getSurname())
				.nickname(userProfileItem.getNickname())
				.email(userProfileItem.getEmail())
				.profilePictureUrl(userProfileItem.getProfilePictureUrl())
				.roles(userProfileItem.getRoles())
				.lastLogonDateTime(userProfileItem.getLastLogonDateTime())
				.registrationDateTime(userProfileItem.getRegistrationDateTime())
				.build() : null;
	}

	private UserProfileItem valueOf(ExamSlammrUserProfile userProfile) {
		return userProfile != null ? UserProfileItem.builder()
				.id(userProfile.getId())
				.compositeUserId(userProfile.getCompositeUserId())
				.firstname(userProfile.getFirstname())
				.surname(userProfile.getSurname())
				.nickname(userProfile.getNickname())
				.email(userProfile.getEmail())
				.profilePictureUrl(userProfile.getProfilePictureUrl())
				.roles(userProfile.getRoles())
				.lastLogonDateTime(userProfile.getLastLogonDateTime())
				.registrationDateTime(userProfile.getRegistrationDateTime())
				.build() : null;
	}
}
