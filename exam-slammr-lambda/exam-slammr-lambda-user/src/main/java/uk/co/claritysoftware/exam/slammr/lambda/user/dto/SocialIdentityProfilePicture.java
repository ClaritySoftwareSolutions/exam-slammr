package uk.co.claritysoftware.exam.slammr.lambda.user.dto;

import lombok.Builder;
import lombok.Value;

/**
 * Encapsulates the data for a user's Social Identity Profile Picture
 */
@Value
@Builder
public class SocialIdentityProfilePicture {

    private Integer height;

    private Integer width;

    private String url;

}
