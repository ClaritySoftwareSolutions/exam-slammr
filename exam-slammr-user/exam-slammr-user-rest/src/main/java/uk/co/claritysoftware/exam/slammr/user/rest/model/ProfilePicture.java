package uk.co.claritysoftware.exam.slammr.user.rest.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

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

    @JsonCreator
    private ProfilePicture(@JsonProperty("height") Integer height,
			@JsonProperty("width") Integer width,
			@JsonProperty("url") String url) {
    	this.height = height;
    	this.width = width;
    	this.url = url;
	}

}
