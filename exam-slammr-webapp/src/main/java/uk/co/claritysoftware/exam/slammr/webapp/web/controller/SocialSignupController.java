package uk.co.claritysoftware.exam.slammr.webapp.web.controller;

import static com.google.common.collect.Sets.newHashSet;
import static java.util.Collections.emptyList;

import java.security.Principal;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Set;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.SocialAuthenticationToken;
import org.springframework.social.security.SocialUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import uk.co.claritysoftware.exam.slammr.webapp.factory.SocialUserSignUpFactory;
import uk.co.claritysoftware.exam.slammr.webapp.service.UserProfileService;
import uk.co.claritysoftware.exam.slammr.webapp.service.model.ExamSlammrUserProfile;
import uk.co.claritysoftware.exam.slammr.webapp.web.model.SocialUserSignUp;

import lombok.extern.slf4j.Slf4j;

/**
 * Controller to handle user sign-ups fusing their social identity
 */
@Slf4j
@Controller
@RequestMapping("/signup")
public class SocialSignupController {

	private final ProviderSignInUtils providerSignInUtils;

	private final UserProfileService userProfileService;

	@Autowired
	public SocialSignupController(ProviderSignInUtils providerSignInUtils, UserProfileService userProfileService) {
		this.providerSignInUtils = providerSignInUtils;
		this.userProfileService = userProfileService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String getSignupForm(Principal principal, Model model, WebRequest webRequest) {

		Connection<?> connection = providerSignInUtils.getConnectionFromSession(webRequest);

		if (principal == null && connection != null) {
			UserProfile userProfile = connection.fetchUserProfile();
			SocialUserSignUp socialUserSignUp = SocialUserSignUpFactory.valueOf(userProfile);
			model.addAttribute("form", socialUserSignUp);
			model.addAttribute("socialProvider", connection.getKey().getProviderId());
			return "signup";

		} else {
			return "redirect:/";
		}
	}

	@RequestMapping(method=RequestMethod.POST)
	public String handleSignup(@Valid SocialUserSignUp socialUserSignUp, WebRequest webRequest) {

		Connection<?> connection = providerSignInUtils.getConnectionFromSession(webRequest);
		String compositeUserId = connection.getKey().toString();

		ExamSlammrUserProfile newUserProfile = valueOf(socialUserSignUp, connection);
		userProfileService.saveNewUserProfile(newUserProfile);

		SocialUser socialUser = new SocialUser(compositeUserId, "", emptyList());
		SocialAuthenticationToken authenticationToken = new SocialAuthenticationToken(connection, socialUser, null, socialUser.getAuthorities());

		SecurityContextHolder.getContext().setAuthentication(authenticationToken);

		providerSignInUtils.doPostSignUp(compositeUserId, webRequest);

		return "redirect:/";
	}

	private ExamSlammrUserProfile valueOf(SocialUserSignUp socialUserSignUp, Connection<?> connection) {
		ZonedDateTime now = ZonedDateTime.now().withZoneSameInstant(ZoneOffset.UTC);
		Set<String> userRoles = newHashSet("USER");
		String compositeUserId = connection.getKey().toString();
		String profilePicUrl = connection.getImageUrl();

		return ExamSlammrUserProfile.builder()
				.compositeUserId(compositeUserId)
				.firstname(socialUserSignUp.getFirstName())
				.surname(socialUserSignUp.getLastName())
				.nickname(socialUserSignUp.getNickName())
				.email(socialUserSignUp.getEmail())
				.profilePictureUrl(profilePicUrl)
				.roles(userRoles)
				.lastLogonDateTime(now)
				.registrationDateTime(now)
				.build();
	}

}