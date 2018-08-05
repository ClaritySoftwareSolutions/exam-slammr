package uk.co.claritysoftware.exam.slammr.rest.user.web.model;

import lombok.Builder;
import lombok.Value;

/**
 * Encapsulates the data for a new User registration request
 */
@Value
@Builder
public final class UserRegistrationRequest {

    private String firstname;

    private String surname;

    private String nickname;

    private String email;

    private String profilePictureUrl;

}
