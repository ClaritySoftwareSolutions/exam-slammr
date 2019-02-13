package uk.co.claritysoftware.exam.slammr.webapp.web.model.user

import org.hibernate.validator.constraints.Length
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

/**
 * Class encapsulating fields to support a user sign up from a social provider
 */
data class SocialUserSignUp(
		@field:NotBlank
		@field:Length(max = 30)
		var firstName: String,

		@field:NotBlank
		@field:Length(max = 30)
		var lastName: String,

		@field:NotBlank
		@field:Length(max = 30)
		var nickName: String,

		@field:NotBlank
		@field:Email
		@field:Length(max = 64)
		var email: String
)
