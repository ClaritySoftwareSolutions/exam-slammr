package uk.co.claritysoftware.exam.slammr.webapp.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.social.security.SocialUser
import org.springframework.social.security.SocialUserDetails
import org.springframework.social.security.SocialUserDetailsService
import org.springframework.stereotype.Service
import uk.co.claritysoftware.exam.slammr.webapp.service.UserProfileService
import java.util.stream.Collectors

/**
 * Implementation of Spring Security Spring Social [SocialUserDetailsService]
 */
@Service
class ExamSlammrUserDetailsService(@Autowired val userProfileService: UserProfileService) : SocialUserDetailsService {

	@Throws(UsernameNotFoundException::class)
	override fun loadUserByUserId(userId: String): SocialUserDetails {
		return userProfileService.getUserProfileByUserId(userId)
				.map { userProfileItem ->
					SocialUser(userId, "", userProfileItem.roles.stream()
							.map { role -> SimpleGrantedAuthority(role) }
							.collect(Collectors.toList()))
				}
				.orElseThrow { UsernameNotFoundException("UserProfile with id $userId not found in repository") }
	}

}