package uk.co.claritysoftware.exam.slammr.lambda.user.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Data;
import uk.co.claritysoftware.exam.slammr.lambda.user.jackson.SocialIdentityProfilePictureDeserializer;

/**
 * Encapsulates the data for a user's Social Identity
 */
@Data
@Builder
public class SocialIdentity {

    private String id;

    private String name;

    private String email;

    private SocialIdentityProfilePicture picture;

    @JsonCreator
    private SocialIdentity(@JsonProperty("id") String id,
                           @JsonProperty("name") String name,
                           @JsonProperty("email") String email,
                           @JsonProperty("picture") @JsonDeserialize(using = SocialIdentityProfilePictureDeserializer.class) SocialIdentityProfilePicture picture) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }
}
