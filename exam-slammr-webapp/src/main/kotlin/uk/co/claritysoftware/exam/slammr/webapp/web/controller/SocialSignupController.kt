package uk.co.claritysoftware.exam.slammr.webapp.web.controller

import org.slf4j.LoggerFactory
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.social.connect.Connection
import org.springframework.social.connect.web.ProviderSignInUtils
import org.springframework.social.security.SocialAuthenticationToken
import org.springframework.social.security.SocialUser
import org.springframework.stereotype.Controller
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.ModelAndView
import uk.co.claritysoftware.exam.slammr.webapp.factory.valueOf
import uk.co.claritysoftware.exam.slammr.webapp.service.UserProfileService
import uk.co.claritysoftware.exam.slammr.webapp.service.model.user.ExamSlammrUserProfile
import uk.co.claritysoftware.exam.slammr.webapp.web.model.user.SocialUserSignUp
import java.security.Principal
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.util.stream.Collectors
import javax.validation.Valid

/**
 * Controller to handle user sign-ups using a user's social identity.
 */
@Controller
@RequestMapping("/signup")
class SocialSignupController(private val providerSignInUtils: ProviderSignInUtils, private val userProfileService: UserProfileService) {

	private companion object {
		val log = LoggerFactory.getLogger(SocialSignupController::class.java)
	}

	/**
	 * Endpoint called via a redirect from Spring Social's `
	 * /auth/<provider>` endpoints. The user has authenticated
	 * with the social provider, and this is the callback endpoint to allow us to present a 'new user profile sign up form'
	 * to create the new user profile.
	 */
	@GetMapping
	fun getSignupForm(principal: Principal?, webRequest: WebRequest): ModelAndView {
		val connection = providerSignInUtils.getConnectionFromSession(webRequest)

		if (principal == null && connection != null) {
			val userProfile = connection.fetchUserProfile()
			val socialUserSignUp = valueOf(userProfile)
			return createSignupViewHidingBindErrors(socialUserSignUp, connection.key.providerId)

		} else {
			return ModelAndView("redirect:/")
		}
	}

	/**
	 * Endpoint to handle the processing of our user profile sign up form.
	 *
	 *
	 * Creates a new user profile record, and sets the security context authentication such that the user is logged in.
	 */
	@PostMapping
	fun handleSignup(@Valid @ModelAttribute("form") form: SocialUserSignUp, bindingResult: BindingResult, webRequest: WebRequest): ModelAndView {

		val connection = providerSignInUtils.getConnectionFromSession(webRequest)

		if (bindingResult.hasErrors()) {
			return createSignupViewShowingBindErrors(form, connection.key.providerId)
		}

		val compositeUserId = connection.key.toString()

		val newUserProfile = valueOf(form, connection)
		userProfileService.saveUserProfile(newUserProfile)

		val socialUser = SocialUser(compositeUserId, "",
				newUserProfile.roles!!.stream()
						.map { SimpleGrantedAuthority(it) }
						.collect(Collectors.toList()))
		val authenticationToken = SocialAuthenticationToken(connection, socialUser, null, socialUser.authorities)

		SecurityContextHolder.getContext().authentication = authenticationToken

		providerSignInUtils.doPostSignUp(compositeUserId, webRequest)

		return ModelAndView("redirect:/")
	}

	private fun createSignupViewShowingBindErrors(form: SocialUserSignUp, socialProvider: String): ModelAndView {
		val modelAndView = ModelAndView("auth/signup")
		modelAndView.addObject("socialProvider", socialProvider)
		modelAndView.addObject("showBindErrors", true)
		modelAndView.addObject("form", form)
		return modelAndView
	}

	private fun createSignupViewHidingBindErrors(form: SocialUserSignUp, socialProvider: String): ModelAndView {
		val modelAndView = ModelAndView("auth/signup")
		modelAndView.addObject("socialProvider", socialProvider)
		modelAndView.addObject("showBindErrors", false)
		modelAndView.addObject("form", form)
		return modelAndView
	}

	private fun valueOf(socialUserSignUp: SocialUserSignUp, connection: Connection<*>): ExamSlammrUserProfile {
		val now = ZonedDateTime.now().withZoneSameInstant(ZoneOffset.UTC)
		val userRoles = setOf("USER")
		val compositeUserId = connection.key.toString()
		val profilePicUrl = connection.imageUrl

		return ExamSlammrUserProfile(
				compositeUserId = compositeUserId,
				firstname = socialUserSignUp.firstName,
				surname = socialUserSignUp.lastName,
				nickname = socialUserSignUp.nickName,
				email = socialUserSignUp.email,
				profilePictureUrl = profilePicUrl,
				roles = userRoles,
				lastLogonDateTime = now,
				registrationDateTime = now)
	}

}