package uk.co.claritysoftware.exam.slammr.rest.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

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

    @JsonCreator
    private UserProfile(@JsonProperty("id") String id,
                           @JsonProperty("name") String name,
                           @JsonProperty("email") String email,
                           @JsonProperty("profilePicture") ProfilePicture picture) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }
}
