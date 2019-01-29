package uk.co.claritysoftware.exam.slammr.webapp.factory

import org.springframework.social.connect.UserProfile
import uk.co.claritysoftware.exam.slammr.webapp.web.model.user.SocialUserSignUp

/**
 * Factory class to create {@link SocialUserSignUp} instances
 */

/**
 * Create a [SocialUserSignUp] from a Spring Social [UserProfile]
 *
 * @param userProfile the UserProfile
 * @return a populated SocialUserSignup instance
 */
fun valueOf(userProfile: UserProfile): SocialUserSignUp {
	return SocialUserSignUp(
			firstName = userProfile.firstName,
			lastName = userProfile.lastName,
			email = userProfile.email,
			nickName = userProfile.name)
}
