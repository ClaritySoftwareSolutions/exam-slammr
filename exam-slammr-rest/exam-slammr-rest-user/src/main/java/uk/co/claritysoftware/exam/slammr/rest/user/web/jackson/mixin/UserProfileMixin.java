package uk.co.claritysoftware.exam.slammr.rest.user.web.jackson.mixin;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;

public abstract class UserProfileMixin {

    @JsonCreator
    public UserProfileMixin(
            @JsonProperty("id") String id,
            @JsonProperty("firstname") String firstname,
            @JsonProperty("surname") String surname,
            @JsonProperty("nickname") String nickname,
            @JsonProperty("email") String email,
            @JsonProperty("profilePictureUrl") String profilePictureUrl,
            @JsonProperty("registrationDateTime") ZonedDateTime registrationDateTime,
            @JsonProperty("lastLogonDateTime") ZonedDateTime lastLogonDateTime) {
    }
}
