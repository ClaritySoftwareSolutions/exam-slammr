package uk.co.claritysoftware.exam.slammr.webapp.web.controller

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.argThat
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.social.connect.Connection
import org.springframework.social.connect.ConnectionData
import org.springframework.social.security.SocialAuthenticationToken
import org.springframework.social.security.SocialUser
import org.springframework.web.context.request.NativeWebRequest
import uk.co.claritysoftware.exam.slammr.webapp.service.UserProfileService
import uk.co.claritysoftware.exam.slammr.webapp.testsupport.service.model.user.ExamSlammrUserProfileTestDataFactory.Companion.mrBurnsExamSlammrUserProfile
import uk.co.claritysoftware.exam.slammr.webapp.web.exception.ExamSlammrUserProfileNotFoundException
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.util.*

/**
 * Unit test class for [SpringSocialSigninAdapter]
 */
@RunWith(MockitoJUnitRunner::class)
class SpringSocialSigninAdapterTest {

	@Mock
	private lateinit var userProfileService: UserProfileService

	@InjectMocks
	private lateinit var signinAdapter: SpringSocialSigninAdapter

	@After
	fun resetSecurityContext() {
		SecurityContextHolder.clearContext()
	}

	@Test
	fun shouldSignIn() {
		// Given
		val userId = "facebook:12345"
		val connection = mock(Connection::class.java)
		val request = mock(NativeWebRequest::class.java)

		val connectionData = ConnectionData("facebook", "12345", "Mr Burns", null, null, null, null, null, null)
		given(connection.createData()).willReturn(connectionData)

		val mrBurnsUserProfile = mrBurnsExamSlammrUserProfile()
				.copy(compositeUserId=userId)
		given(userProfileService.getUserProfileByUserId(any()))
				.willReturn(Optional.of(mrBurnsUserProfile))

		val earliestLastLogonDate = ZonedDateTime.now().withZoneSameInstant(ZoneOffset.UTC)

		SecurityContextHolder.clearContext()

		// When
		val postSignInRedirectUrl = signinAdapter.signIn(userId, connection, request)

		// Then
		then(userProfileService).should().getUserProfileByUserId(userId)
		then(userProfileService).should().saveUserProfile(argThat {
			assertThat(this)
					.isEqualToIgnoringGivenFields(mrBurnsUserProfile, "lastLogonDateTime")
			assertThat(this.lastLogonDateTime)
					.isAfterOrEqualTo(earliestLastLogonDate)
			true
		})
		assertThat(postSignInRedirectUrl).isNull()
		val socialAuthenticationToken = SecurityContextHolder.getContext().authentication as SocialAuthenticationToken
		assertThat(socialAuthenticationToken.providerId)
				.isEqualTo("facebook")
		val socialUser = SecurityContextHolder.getContext().authentication.principal as SocialUser
		assertThat(socialUser.userId)
				.isEqualTo("facebook:12345")
	}

	@Test
	fun shouldFailToSignInGivenExamSlammrUserProfileNotFound() {
		// Given
		val userId = "facebook:12345"
		val connection = mock(Connection::class.java)
		val request = mock(NativeWebRequest::class.java)

		given(userProfileService.getUserProfileByUserId(any()))
				.willReturn(Optional.empty())

		// When
		val throwable = catchThrowable { signinAdapter.signIn(userId, connection, request) }

		// Then
		then(userProfileService).should().getUserProfileByUserId(userId)
		assertThat(throwable)
				.`as`("Expect a ExamSlammrUserProfileNotFoundException to have been thrown")
				.isInstanceOf(ExamSlammrUserProfileNotFoundException::class.java)
				.hasMessage("ExamSlammrUserProfile with id facebook:12345 not found")
	}
}