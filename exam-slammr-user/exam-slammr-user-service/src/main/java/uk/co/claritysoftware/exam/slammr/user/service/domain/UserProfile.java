package uk.co.claritysoftware.exam.slammr.user.service.domain;

import lombok.Builder;
import lombok.Value;

/**
 * Encapsulates the data for a user's Social Identity
 */
@Value
@Builder
public class UserProfile {

    private String id;

    private String name;

    private String email;

    private ProfilePicture picture;
}
