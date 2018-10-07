package uk.co.claritysoftware.exam.slammr.webapp.web.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static uk.co.claritysoftware.exam.slammr.webapp.testsupport.service.model.user.ExamSlammrUserProfileTestDataFactory.mrBurnsExamSlammrUserProfile;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Optional;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.security.SocialAuthenticationToken;
import org.springframework.social.security.SocialUser;
import org.springframework.web.context.request.NativeWebRequest;
import uk.co.claritysoftware.exam.slammr.webapp.service.UserProfileService;
import uk.co.claritysoftware.exam.slammr.webapp.service.model.user.ExamSlammrUserProfile;
import uk.co.claritysoftware.exam.slammr.webapp.web.exception.ExamSlammrUserProfileNotFoundException;

/**
 * Unit test class for {@link SpringSocialSigninAdapter}
 */
@RunWith(MockitoJUnitRunner.class)
public class SpringSocialSigninAdapterTest {

	@Mock
	private UserProfileService userProfileService;

	@InjectMocks
	private SpringSocialSigninAdapter signinAdapter;

	@After
	public void resetSecurityContext() {
		SecurityContextHolder.clearContext();
	}

	@Test
	public void shouldSignIn() {
		// Given
		String userId = "facebook:12345";
		Connection connection = mock(Connection.class);
		NativeWebRequest request = mock(NativeWebRequest.class);

		ConnectionData connectionData = new ConnectionData("facebook", "12345", "Mr Burns", null, null, null, null, null, null);
		given(connection.createData()).willReturn(connectionData);

		ExamSlammrUserProfile mrBurnsUserProfile = mrBurnsExamSlammrUserProfile()
				.compositeUserId(userId)
				.build();
		given(userProfileService.getUserProfileByUserId(any()))
				.willReturn(Optional.of(mrBurnsUserProfile));

		ZonedDateTime earliestLastLogonDate = ZonedDateTime.now().withZoneSameInstant(ZoneOffset.UTC);

		SecurityContextHolder.clearContext();

		// When
		String postSignInRedirectUrl = signinAdapter.signIn(userId, connection, request);

		// Then
		then(userProfileService).should().getUserProfileByUserId(userId);
		then(userProfileService).should().saveUserProfile(argThat(savedUserProfile -> {
			assertThat(savedUserProfile)
					.isEqualToIgnoringGivenFields(mrBurnsUserProfile, "lastLogonDateTime");
			assertThat(savedUserProfile.getLastLogonDateTime())
					.isAfterOrEqualTo(earliestLastLogonDate);
			return true;
		}));
		assertThat(postSignInRedirectUrl).isNull();
		SocialAuthenticationToken socialAuthenticationToken = (SocialAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
		assertThat(socialAuthenticationToken.getProviderId())
				.isEqualTo("facebook");
		SocialUser socialUser = (SocialUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		assertThat(socialUser.getUserId())
				.isEqualTo("facebook:12345");
	}

	@Test
	public void shouldFailToSignInGivenExamSlammrUserProfileNotFound() {
		// Given
		String userId = "facebook:12345";
		Connection connection = mock(Connection.class);
		NativeWebRequest request = mock(NativeWebRequest.class);

		given(userProfileService.getUserProfileByUserId(any()))
				.willReturn(Optional.empty());

		// When
		Throwable throwable = catchThrowable(() -> signinAdapter.signIn(userId, connection, request));

		// Then
		then(userProfileService).should().getUserProfileByUserId(userId);
		assertThat(throwable)
				.as("Expect a ExamSlammrUserProfileNotFoundException to have been thrown")
				.isInstanceOf(ExamSlammrUserProfileNotFoundException.class)
				.hasMessage("ExamSlammrUserProfile with id facebook:12345 not found");
	}
}