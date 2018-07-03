package uk.co.claritysoftware.exam.slammr.user.rest.model;

import lombok.Builder;
import lombok.Value;

import java.time.ZonedDateTime;

/**
 * Encapsulates the data for a User Profile
 */
@Value
@Builder
public final class UserProfile {

    private String id;

    private String firstname;

    private String surname;

    private String nickname;

    private String email;

    private String profilePictureUrl;

    private ZonedDateTime registrationDateTime;

    private ZonedDateTime lastLogonDateTime;

}
