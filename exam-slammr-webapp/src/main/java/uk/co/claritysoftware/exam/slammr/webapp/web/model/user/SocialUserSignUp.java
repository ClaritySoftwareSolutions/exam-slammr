package uk.co.claritysoftware.exam.slammr.webapp.web.model.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

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
	@Length(max = 30)
	private String firstName;

	@NotBlank
	@Length(max = 30)
	private String lastName;

	@NotBlank
	@Length(max = 30)
	private String nickName;

	@NotBlank
	@Email
	@Length(max = 64)
	private String email;
}
