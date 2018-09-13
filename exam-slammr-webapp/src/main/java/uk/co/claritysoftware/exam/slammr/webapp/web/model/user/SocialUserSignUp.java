package uk.co.claritysoftware.exam.slammr.webapp.web.model.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class encapsulating fields to support a user sign up from a social provider
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class SocialUserSignUp {

	@NotBlank
	@Max(30)
	private String firstName;

	@NotBlank
	@Max(30)
	private String lastName;

	@NotBlank
	@Max(30)
	private String nickName;

	@NotBlank
	@Email
	@Max(64)
	private String email;
}
