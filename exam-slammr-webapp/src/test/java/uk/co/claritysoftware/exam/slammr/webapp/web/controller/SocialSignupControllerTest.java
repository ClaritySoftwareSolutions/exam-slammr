package uk.co.claritysoftware.exam.slammr.webapp.web.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static uk.co.claritysoftware.exam.slammr.webapp.testsupport.web.model.user.SocialUserSignUpTestDataFactory.bartSimposonsUserSignup;

import java.security.Principal;
import java.util.Map;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import com.google.common.collect.ImmutableMap;
import uk.co.claritysoftware.exam.slammr.webapp.factory.SocialUserSignUpFactory;
import uk.co.claritysoftware.exam.slammr.webapp.service.UserProfileService;
import uk.co.claritysoftware.exam.slammr.webapp.web.model.user.SocialUserSignUp;

/**
 * Unit test class for {@link SocialSignupController}
 */
@RunWith(MockitoJUnitRunner.class)
public class SocialSignupControllerTest {

	@Mock
	private ProviderSignInUtils providerSignInUtils;

	@Mock
	private UserProfileService userProfileService;

	@InjectMocks
	private SocialSignupController socialSignupController;

	@Test
	public void shouldNotGetSignupFormGivenUserAlreadyLoggedInAndHasAPrincipal() {
		// Given
		Principal principal = mock(Principal.class);
		WebRequest webRequest = mock(WebRequest.class);

		// When
		ModelAndView modelAndView = socialSignupController.getSignupForm(principal, webRequest);

		// Then
		assertThat(modelAndView.getViewName())
				.isEqualTo("redirect:/");
	}

	@Test
	public void shouldNotGetSignupFormGivenNullSocialProviderConnection() {
		// Given
		Principal principal = null;
		WebRequest webRequest = mock(WebRequest.class);
		given(providerSignInUtils.getConnectionFromSession(any()))
				.willReturn(null);

		// When
		ModelAndView modelAndView = socialSignupController.getSignupForm(principal, webRequest);

		// Then
		assertThat(modelAndView.getViewName())
				.isEqualTo("redirect:/");
	}

	@Test
	public void shouldGetSignupForm() {
		// Given
		Principal principal = null;
		WebRequest webRequest = mock(WebRequest.class);

		Connection connection = mock(Connection.class);
		given(providerSignInUtils.getConnectionFromSession(any()))
				.willReturn(connection);

		UserProfile userProfile = new UserProfile("facebook:12345", "Mr Burns", "Montgomery", "Burns", "monty.burns@springfield.com", "facebook:12345");
		given(connection.fetchUserProfile())
				.willReturn(userProfile);

		ConnectionKey connectionKey = new ConnectionKey("facebook", "12345");
		given(connection.getKey())
				.willReturn(connectionKey);

		Map expectedModel = ImmutableMap.builder()
				.put("form", SocialUserSignUpFactory.valueOf(userProfile))
				.put("socialProvider", "facebook")
				.put("showBindErrors", false)
				.build();

		// When
		ModelAndView modelAndView = socialSignupController.getSignupForm(principal, webRequest);

		// Then
		assertThat(modelAndView.getViewName())
				.isEqualTo("auth/signup");
		assertThat(modelAndView.getModel())
				.isEqualTo(expectedModel);
	}

	@Test
	public void shouldNotHandleSignupGivenBindingErrors() {
		// Given
		WebRequest webRequest = mock(WebRequest.class);
		BindingResult bindingResult = mock(BindingResult.class);

		Connection connection = mock(Connection.class);
		given(providerSignInUtils.getConnectionFromSession(any()))
				.willReturn(connection);

		ConnectionKey connectionKey = new ConnectionKey("facebook", "12345");
		given(connection.getKey())
				.willReturn(connectionKey);

		given(bindingResult.hasErrors()).willReturn(true);

		SocialUserSignUp socialUserSignUp = bartSimposonsUserSignup().build();

		Map expectedModel = ImmutableMap.builder()
				.put("form", socialUserSignUp)
				.put("socialProvider", "facebook")
				.put("showBindErrors", true)
				.build();

		// When
		ModelAndView modelAndView = socialSignupController.handleSignup(socialUserSignUp, bindingResult, webRequest);

		// Then
		then(providerSignInUtils).should().getConnectionFromSession(webRequest);
		assertThat(modelAndView.getViewName())
				.isEqualTo("auth/signup");
		assertThat(modelAndView.getModel())
				.isEqualTo(expectedModel);
	}

}