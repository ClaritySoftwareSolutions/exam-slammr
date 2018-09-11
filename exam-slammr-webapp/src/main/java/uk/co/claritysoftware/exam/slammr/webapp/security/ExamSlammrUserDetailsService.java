package uk.co.claritysoftware.exam.slammr.webapp.security;

import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Service;
import uk.co.claritysoftware.exam.slammr.webapp.service.UserProfileService;

/**
 * Implementation of Spring Security Spring Social {@link SocialUserDetailsService}
 */
@Service
public class ExamSlammrUserDetailsService implements SocialUserDetailsService {

	private final UserProfileService userProfileService;

	@Autowired
	public ExamSlammrUserDetailsService(UserProfileService userProfileService) {
		this.userProfileService = userProfileService;
	}

	@Override
	public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
		return userProfileService.getUserProfileByUserId(userId)
				.map(userProfileItem -> new SocialUser(userId, "", userProfileItem.getRoles().stream()
						.map(SimpleGrantedAuthority::new)
						.collect(Collectors.toList())))
				.orElseThrow(() -> new UsernameNotFoundException("UserProfile with id " + userId + " not found in repository"));
	}

}
