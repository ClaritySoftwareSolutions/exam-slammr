package uk.co.claritysoftware.exam.slammr.webapp.web.model.user

import org.hibernate.validator.constraints.Length
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

/**
 * Class encapsulating fields to support a user sign up from a social provider
 */
data class SocialUserSignUp(
		@NotBlank
		@Length(max = 30)
		val firstName: String,

		@NotBlank
		@Length(max = 30)
		val lastName: String,

		@NotBlank
		@Length(max = 30)
		val nickName: String,

		@NotBlank
		@Email
		@Length(max = 64)
		val email: String
)
