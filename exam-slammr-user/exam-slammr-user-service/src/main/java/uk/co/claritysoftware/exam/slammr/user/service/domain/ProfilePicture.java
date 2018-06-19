package uk.co.claritysoftware.exam.slammr.user.service.domain;

import lombok.Builder;
import lombok.Value;

/**
 * Encapsulates the data for a user's Social Identity Profile Picture
 */
@Value
@Builder
public class ProfilePicture {

    private Integer height;

    private Integer width;

    private String url;
}
