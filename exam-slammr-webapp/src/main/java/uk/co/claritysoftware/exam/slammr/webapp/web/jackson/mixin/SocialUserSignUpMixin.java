package uk.co.claritysoftware.exam.slammr.webapp.web.jackson.mixin;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import uk.co.claritysoftware.exam.slammr.webapp.web.model.SocialUserSignUp;

/**
 * Jackson mixin to tell Jackson how to deserialize into a {@link SocialUserSignUp}
 */
public abstract class SocialUserSignUpMixin {

	@JsonCreator
	public SocialUserSignUpMixin(
			@JsonProperty("firstName") String firstName,
			@JsonProperty("lastName") String lastName,
			@JsonProperty("nickName") String nickName,
			@JsonProperty("email") String email
	) {}
}
