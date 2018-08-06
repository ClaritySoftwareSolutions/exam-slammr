package uk.co.claritysoftware.exam.slammr.rest.user.web.model;

import java.time.ZonedDateTime;

import lombok.Builder;
import lombok.Value;

/**
 * Encapsulates the data for a User Profile
 */
@Value
@Builder
public final class UserProfile {

    private String firstname;

    private String surname;

    private String nickname;

    private String email;

    private String profilePictureUrl;

    private ZonedDateTime registrationDateTime;

    private ZonedDateTime lastLogonDateTime;

}
