package uk.co.claritysoftware.exam.slammr.user.rest.factory;

import org.springframework.stereotype.Component;
import uk.co.claritysoftware.exam.slammr.user.rest.model.UserProfile;
import uk.co.claritysoftware.exam.slammr.user.service.dynamodb.UserProfileItem;

/**
 * Factory class to create {@link UserProfile} instances
 */
@Component
public class UserProfileFactory {

    public UserProfile valueOf(UserProfileItem userProfileItem) {
        return UserProfile.builder()
                .id(userProfileItem.getWebFederatedUserId())
                .firstname(userProfileItem.getFirstname())
                .surname(userProfileItem.getSurname())
                .nickname(userProfileItem.getNickname())
                .email(userProfileItem.getEmail())
                .profilePictureUrl(userProfileItem.getProfilePictureUrl())
                .registrationDateTime(userProfileItem.getRegistrationDateTime())
                .lastLogonDateTime(userProfileItem.getLastLogonDateTime())
                .build();
    }
}