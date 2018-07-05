package uk.co.claritysoftware.exam.slammr.user.rest.factory;

import org.springframework.stereotype.Component;
import uk.co.claritysoftware.exam.slammr.user.rest.model.UserRegistrationRequest;
import uk.co.claritysoftware.exam.slammr.user.service.dynamodb.UserProfileItem;

import java.time.ZonedDateTime;

/**
 * Factory class to create {@link UserProfileItem} instances
 */
@Component
public class UserProfileItemFactory {

    public UserProfileItem valueOf(UserRegistrationRequest userRegistrationRequest, String identityId) {
        ZonedDateTime now = ZonedDateTime.now();
        return UserProfileItem.builder()
                .webFederatedUserId(identityId)
                .firstname(userRegistrationRequest.getFirstname())
                .surname(userRegistrationRequest.getSurname())
                .nickname(userRegistrationRequest.getNickname())
                .email(userRegistrationRequest.getEmail())
                .profilePictureUrl(userRegistrationRequest.getProfilePictureUrl())
                .registrationDateTime(now)
                .lastLogonDateTime(now)
                .build();
    }
}
