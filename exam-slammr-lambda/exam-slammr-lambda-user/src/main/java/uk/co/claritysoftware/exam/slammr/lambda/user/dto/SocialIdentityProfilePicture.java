package uk.co.claritysoftware.exam.slammr.lambda.user.dto;

import lombok.Builder;
import lombok.Data;

/**
 * Encapsulates the data for a user's Social Identity Profile Picture
 */
@Data
@Builder
public class SocialIdentityProfilePicture {

    private Integer height;

    private Integer width;

    private String url;

}
