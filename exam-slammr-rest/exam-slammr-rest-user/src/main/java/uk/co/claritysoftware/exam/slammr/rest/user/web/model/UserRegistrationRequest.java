package uk.co.claritysoftware.exam.slammr.rest.user.web.model;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * Encapsulates the data for a new User registration request
 */
@Value
@Builder
public final class UserRegistrationRequest {

    @NotEmpty
    private String firstname;

    @NotEmpty
    private String surname;

    private String nickname;

    @Email
    private String email;

    private String profilePictureUrl;

}
