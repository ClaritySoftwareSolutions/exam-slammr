package uk.co.claritysoftware.exam.slammr.webapp.web.controller

import org.slf4j.LoggerFactory
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.social.connect.Connection
import org.springframework.social.connect.web.SignInAdapter
import org.springframework.social.security.SocialAuthenticationToken
import org.springframework.social.security.SocialUser
import org.springframework.stereotype.Component
import org.springframework.web.context.request.NativeWebRequest
import uk.co.claritysoftware.exam.slammr.webapp.service.UserProfileService
import uk.co.claritysoftware.exam.slammr.webapp.web.exception.ExamSlammrUserProfileNotFoundException
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.util.stream.Collectors

/**
 * [SignInAdapter] to adapt a Social Provider sign in to an Exam Slammr user profile
 */
@Component
class SpringSocialSigninAdapter(private val userProfileService: UserProfileService) : SignInAdapter {

	private companion object {
		val log = LoggerFactory.getLogger(SpringSocialSigninAdapter::class.java)
	}

	override fun signIn(userId: String, connection: Connection<*>, request: NativeWebRequest): String? {
		log.debug("Attempting to sign in user {}", userId)

		val now = ZonedDateTime.now().withZoneSameInstant(ZoneOffset.UTC)
		val examSlammrUserProfile = userProfileService.getUserProfileByUserId(userId)
				.orElseThrow { ExamSlammrUserProfileNotFoundException(userId) }
				.copy(lastLogonDateTime = now)

		userProfileService.saveUserProfile(examSlammrUserProfile)

		val socialUser = SocialUser(userId, "",
				examSlammrUserProfile.roles!!.stream()
						.map { SimpleGrantedAuthority(it) }
						.collect(Collectors.toList<SimpleGrantedAuthority>()))
		val authenticationToken = SocialAuthenticationToken(connection, socialUser, null, socialUser.authorities)

		SecurityContextHolder.getContext().authentication = authenticationToken

		return null
	}

}