package uk.co.claritysoftware.exam.slammr.rest.user.web.factory;

import org.springframework.stereotype.Component;
import uk.co.claritysoftware.exam.slammr.rest.user.web.model.UserProfile;
import uk.co.claritysoftware.exam.slammr.rest.user.service.dynamodb.UserProfileItem;

/**
 * Factory class to create {@link UserProfile} instances
 */
@Component
public class UserProfileFactory {

    public UserProfile valueOf(UserProfileItem userProfileItem) {
        return UserProfile.builder()
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
