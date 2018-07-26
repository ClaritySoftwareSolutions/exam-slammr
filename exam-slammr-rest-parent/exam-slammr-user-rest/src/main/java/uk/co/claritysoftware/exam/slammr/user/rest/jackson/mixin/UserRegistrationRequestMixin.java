package uk.co.claritysoftware.exam.slammr.user.rest.jackson.mixin;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class UserRegistrationRequestMixin {

    @JsonCreator
    public UserRegistrationRequestMixin(
            @JsonProperty("firstname") String firstname,
            @JsonProperty("surname") String surname,
            @JsonProperty("nickname") String nickname,
            @JsonProperty("email") String email,
            @JsonProperty("profilePictureUrl") String profilePictureUrl) {
    }

}
