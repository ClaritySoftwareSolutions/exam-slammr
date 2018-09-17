package uk.co.claritysoftware.exam.slammr.webapp.service.factory;

import uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.item.user.UserProfileItem;
import uk.co.claritysoftware.exam.slammr.webapp.service.model.user.ExamSlammrUserProfile;

/**
 * Factory class to create instances to and from {@link ExamSlammrUserProfile} instances
 */
public class ExamSlammrUserProfileFactory {

    /**
     * Creates a new {@link ExamSlammrUserProfile} from the specified {@link UserProfileItem}
     *
     * @param userProfileItem the UserProfileItem to create the ExamSlammrUserProfile from
     * @return a populated ExamSlammrUserProfile instance
     */
    public static ExamSlammrUserProfile valueOf(UserProfileItem userProfileItem) {
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

    /**
     * Creates a new {@link UserProfileItem} from the specified {@link ExamSlammrUserProfile}
     *
     * @param userProfile the ExamSlammrUserProfile to create the UserProfileItem from
     * @return a populated UserProfileItem instance
     */
    public static UserProfileItem valueOf(ExamSlammrUserProfile userProfile) {
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
