package uk.co.claritysoftware.exam.slammr.rest.user.web.jackson.mixin;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import uk.co.claritysoftware.exam.slammr.rest.user.web.model.UserRegistrationRequest;

/**
 * Jackson mixin to tell Jackson how to deserialize into a {@link UserRegistrationRequest}
 */
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
