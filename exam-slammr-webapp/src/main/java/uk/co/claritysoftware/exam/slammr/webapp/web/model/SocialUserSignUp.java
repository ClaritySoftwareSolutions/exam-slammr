package uk.co.claritysoftware.exam.slammr.webapp.web.model;

import javax.validation.constraints.Email;
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
public class SocialUserSignUp {

	@NotBlank
	private String firstName;

	@NotBlank
	private String lastName;

	@NotBlank
	private String nickName;

	@NotBlank
	@Email
	private String email;
}
