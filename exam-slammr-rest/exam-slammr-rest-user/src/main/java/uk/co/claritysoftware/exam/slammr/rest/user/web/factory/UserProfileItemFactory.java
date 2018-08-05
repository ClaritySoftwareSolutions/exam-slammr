package uk.co.claritysoftware.exam.slammr.rest.user.web.factory;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import org.springframework.stereotype.Component;
import uk.co.claritysoftware.exam.slammr.rest.user.web.model.UserRegistrationRequest;
import uk.co.claritysoftware.exam.slammr.rest.user.service.dynamodb.UserProfileItem;

/**
 * Factory class to create {@link UserProfileItem} instances
 */
@Component
public class UserProfileItemFactory {

    public UserProfileItem valueOf(UserRegistrationRequest userRegistrationRequest, String identityId) {
        ZonedDateTime now = ZonedDateTime.now().withZoneSameInstant(ZoneOffset.UTC);
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
