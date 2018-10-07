package uk.co.claritysoftware.exam.slammr.webapp.web.controller;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.social.security.SocialAuthenticationToken;
import org.springframework.social.security.SocialUser;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;
import uk.co.claritysoftware.exam.slammr.webapp.service.UserProfileService;
import uk.co.claritysoftware.exam.slammr.webapp.service.model.user.ExamSlammrUserProfile;
import uk.co.claritysoftware.exam.slammr.webapp.web.exception.ExamSlammrUserProfileNotFoundException;

import lombok.extern.slf4j.Slf4j;

/**
 * {@link SignInAdapter} to adapt a Social Provider sign in to an Exam Slammr user profile
 */
@Slf4j
@Component
public class SpringSocialSigninAdapter implements SignInAdapter {

	private final UserProfileService userProfileService;

	@Autowired
	public SpringSocialSigninAdapter(UserProfileService userProfileService) {
		this.userProfileService = userProfileService;
	}

	@Override
	public String signIn(String userId, Connection<?> connection, NativeWebRequest request) {
		log.debug("Attempting to sign in user {}", userId);

		ZonedDateTime now = ZonedDateTime.now().withZoneSameInstant(ZoneOffset.UTC);
		ExamSlammrUserProfile examSlammrUserProfile = userProfileService.getUserProfileByUserId(userId)
				.orElseThrow(() -> new ExamSlammrUserProfileNotFoundException(userId))
				.toBuilder()
				.lastLogonDateTime(now)
				.build();

		userProfileService.saveUserProfile(examSlammrUserProfile);

		SocialUser socialUser = new SocialUser(userId, "",
				examSlammrUserProfile.getRoles().stream()
						.map(SimpleGrantedAuthority::new)
						.collect(Collectors.toList()));
		SocialAuthenticationToken authenticationToken = new SocialAuthenticationToken(connection, socialUser, null, socialUser.getAuthorities());

		SecurityContextHolder.getContext().setAuthentication(authenticationToken);

		return null;
	}

}
