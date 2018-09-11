package uk.co.claritysoftware.exam.slammr.webapp.factory;

import org.springframework.social.connect.UserProfile;
import uk.co.claritysoftware.exam.slammr.webapp.web.model.SocialUserSignUp;

/**
 * Factory class to create {@link SocialUserSignUp} instances
 */
public class SocialUserSignUpFactory {

	/**
	 * Create a {@link SocialUserSignUp} from a Spring Social {@link UserProfile}
	 *
	 * @param userProfile the UserProfile
	 * @return a populated SocialUserSignup instance
	 */
	public static SocialUserSignUp valueOf(UserProfile userProfile) {
		return SocialUserSignUp.builder()
				.firstName(userProfile.getFirstName())
				.lastName(userProfile.getLastName())
				.email(userProfile.getEmail())
				.nickName(userProfile.getName())
				.build();
	}

}
