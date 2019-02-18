package uk.co.claritysoftware.exam.slammr.webapp.web.controller

import com.google.common.collect.ImmutableMap
import com.nhaarman.mockitokotlin2.any
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import org.springframework.social.connect.Connection
import org.springframework.social.connect.ConnectionKey
import org.springframework.social.connect.UserProfile
import org.springframework.social.connect.web.ProviderSignInUtils
import org.springframework.validation.BindingResult
import org.springframework.web.context.request.WebRequest
import uk.co.claritysoftware.exam.slammr.webapp.factory.valueOf
import uk.co.claritysoftware.exam.slammr.webapp.service.UserProfileService
import uk.co.claritysoftware.exam.slammr.webapp.testsupport.web.model.user.bartSimposonsUserSignup
import java.security.Principal

/**
 * Unit test class for [SocialSignupController]
 */
@RunWith(MockitoJUnitRunner::class)
class SocialSignupControllerTest {

	@Mock
	private lateinit var providerSignInUtils: ProviderSignInUtils

	@Mock
	private lateinit var userProfileService: UserProfileService

	@InjectMocks
	private lateinit var socialSignupController: SocialSignupController

	@Test
	fun shouldNotGetSignupFormGivenUserAlreadyLoggedInAndHasAPrincipal() {
		// Given
		val principal = mock(Principal::class.java)
		val webRequest = mock(WebRequest::class.java)

		// When
		val modelAndView = socialSignupController.getSignupForm(principal, webRequest)

		// Then
		assertThat(modelAndView.viewName)
				.isEqualTo("redirect:/")
	}

	@Test
	fun shouldNotGetSignupFormGivenNullSocialProviderConnection() {
		// Given
		val principal: Principal? = null
		val webRequest = mock(WebRequest::class.java)
		given(providerSignInUtils.getConnectionFromSession(any()))
				.willReturn(null)

		// When
		val modelAndView = socialSignupController.getSignupForm(principal, webRequest)

		// Then
		assertThat(modelAndView.viewName)
				.isEqualTo("redirect:/")
	}

	@Test
	fun shouldGetSignupForm() {
		// Given
		val principal: Principal? = null
		val webRequest = mock(WebRequest::class.java)

		val connection = mock(Connection::class.java)
		given(providerSignInUtils.getConnectionFromSession(any()))
				.willReturn(connection)

		val userProfile = UserProfile("facebook:12345", "Mr Burns", "Montgomery", "Burns", "monty.burns@springfield.com", "facebook:12345")
		given(connection.fetchUserProfile())
				.willReturn(userProfile)

		val connectionKey = ConnectionKey("facebook", "12345")
		given(connection.getKey())
				.willReturn(connectionKey)

		val expectedModel = ImmutableMap.builder<String, Any>()
				.put("form", valueOf(userProfile))
				.put("socialProvider", "facebook")
				.put("showBindErrors", false)
				.build()

		// When
		val modelAndView = socialSignupController.getSignupForm(principal, webRequest)

		// Then
		assertThat(modelAndView.viewName)
				.isEqualTo("auth/signup")
		assertThat(modelAndView.model)
				.isEqualTo(expectedModel)
	}

	@Test
	fun shouldNotHandleSignupGivenBindingErrors() {
		// Given
		val webRequest = mock(WebRequest::class.java)
		val bindingResult = mock(BindingResult::class.java)

		val connection = mock(Connection::class.java)
		given(providerSignInUtils.getConnectionFromSession(any()))
				.willReturn(connection)

		val connectionKey = ConnectionKey("facebook", "12345")
		given(connection.getKey())
				.willReturn(connectionKey)

		given(bindingResult.hasErrors()).willReturn(true)

		val socialUserSignUp = bartSimposonsUserSignup()

		val expectedModel = ImmutableMap.builder<String, Any>()
				.put("form", socialUserSignUp)
				.put("socialProvider", "facebook")
				.put("showBindErrors", true)
				.build()

		// When
		val modelAndView = socialSignupController.handleSignup(socialUserSignUp, bindingResult, webRequest)

		// Then
		then(providerSignInUtils).should().getConnectionFromSession(webRequest)
		assertThat(modelAndView.viewName)
				.isEqualTo("auth/signup")
		assertThat(modelAndView.model)
				.isEqualTo(expectedModel)
	}

}