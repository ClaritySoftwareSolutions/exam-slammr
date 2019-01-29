package uk.co.claritysoftware.exam.slammr.webapp.web.controller;

import static com.google.common.collect.Sets.newHashSet;

import java.security.Principal;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.SocialAuthenticationToken;
import org.springframework.social.security.SocialUser;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import uk.co.claritysoftware.exam.slammr.webapp.factory.SocialUserSignUpFactoryKt;
import uk.co.claritysoftware.exam.slammr.webapp.service.UserProfileService;
import uk.co.claritysoftware.exam.slammr.webapp.service.model.user.ExamSlammrUserProfile;
import uk.co.claritysoftware.exam.slammr.webapp.web.model.user.SocialUserSignUp;

import lombok.extern.slf4j.Slf4j;

/**
 * Controller to handle user sign-ups using a user's social identity.
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

	/**
	 * Endpoint called via a redirect from Spring Social's {@code /auth/<provider>} endpoints. The user has authenticated
	 * with the social provider, and this is the callback endpoint to allow us to present a 'new user profile sign up form'
	 * to create the new user profile.
	 */
	@GetMapping
	public ModelAndView getSignupForm(Principal principal, WebRequest webRequest) {
		Connection<?> connection = providerSignInUtils.getConnectionFromSession(webRequest);

		if (principal == null && connection != null) {
			UserProfile userProfile = connection.fetchUserProfile();
			SocialUserSignUp socialUserSignUp = SocialUserSignUpFactoryKt.valueOf(userProfile);
			return createSignupViewHidingBindErrors(socialUserSignUp, connection.getKey().getProviderId());

		} else {
			return new ModelAndView("redirect:/");
		}
	}

	/**
	 * Endpoint to handle the processing of our user profile sign up form.
	 * <p>
	 * Creates a new user profile record, and sets the security context authentication such that the user is logged in.
	 */
	@PostMapping
	public ModelAndView handleSignup(@Valid @ModelAttribute("form") SocialUserSignUp form, BindingResult bindingResult, WebRequest webRequest) {

		Connection<?> connection = providerSignInUtils.getConnectionFromSession(webRequest);

		if (bindingResult.hasErrors()) {
			return createSignupViewShowingBindErrors(form, connection.getKey().getProviderId());
		}

		String compositeUserId = connection.getKey().toString();

		ExamSlammrUserProfile newUserProfile = valueOf(form, connection);
		userProfileService.saveUserProfile(newUserProfile);

		SocialUser socialUser = new SocialUser(compositeUserId, "",
				newUserProfile.getRoles().stream()
						.map(SimpleGrantedAuthority::new)
						.collect(Collectors.toList()));
		SocialAuthenticationToken authenticationToken = new SocialAuthenticationToken(connection, socialUser, null, socialUser.getAuthorities());

		SecurityContextHolder.getContext().setAuthentication(authenticationToken);

		providerSignInUtils.doPostSignUp(compositeUserId, webRequest);

		return new ModelAndView("redirect:/");
	}

	private ModelAndView createSignupViewShowingBindErrors(SocialUserSignUp form, String socialProvider) {
		ModelAndView modelAndView = new ModelAndView("auth/signup");
		modelAndView.addObject("socialProvider", socialProvider);
		modelAndView.addObject("showBindErrors", true);
		modelAndView.addObject("form", form);
		return modelAndView;
	}

	private ModelAndView createSignupViewHidingBindErrors(SocialUserSignUp form, String socialProvider) {
		ModelAndView modelAndView = new ModelAndView("auth/signup");
		modelAndView.addObject("socialProvider", socialProvider);
		modelAndView.addObject("showBindErrors", false);
		modelAndView.addObject("form", form);
		return modelAndView;
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
