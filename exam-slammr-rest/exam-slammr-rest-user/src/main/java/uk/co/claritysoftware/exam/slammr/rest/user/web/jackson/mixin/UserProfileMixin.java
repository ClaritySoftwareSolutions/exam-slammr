package uk.co.claritysoftware.exam.slammr.rest.user.web.jackson.mixin;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import uk.co.claritysoftware.exam.slammr.rest.user.web.model.UserProfile;

import java.time.ZonedDateTime;

/**
 * Jackson mixin to tell Jackson how to deserialize into a {@link UserProfile}
 */
public abstract class UserProfileMixin {

    @JsonCreator
    public UserProfileMixin(
            @JsonProperty("firstname") String firstname,
            @JsonProperty("surname") String surname,
            @JsonProperty("nickname") String nickname,
            @JsonProperty("email") String email,
            @JsonProperty("profilePictureUrl") String profilePictureUrl,
            @JsonProperty("registrationDateTime") ZonedDateTime registrationDateTime,
            @JsonProperty("lastLogonDateTime") ZonedDateTime lastLogonDateTime) {
    }
}
