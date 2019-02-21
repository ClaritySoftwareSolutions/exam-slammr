package uk.co.claritysoftware.exam.slammr.webapp.web.controller

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView
import uk.co.claritysoftware.exam.slammr.webapp.web.config.SocialProviders
import java.security.Principal

/**
 * Controller to handle registration page requests
 */
@Controller
@RequestMapping("/register")
class RegistrationPageController(private val socialProviders: SocialProviders) {

	private companion object {
		val log = LoggerFactory.getLogger(RegistrationPageController::class.java)
	}

	@GetMapping
	fun getRegistrationPage(principal: Principal?): ModelAndView {

		return principal
				?.let { ModelAndView("redirect:/") }
				?: ModelAndView("auth/registration")
						.addObject("facebookConfigured", socialProviders.isFacebookConfigured)
						.addObject("twitterConfigured", socialProviders.isTwitterConfigured)
						.addObject("linkedInConfigured", socialProviders.isLinkedinConfigured)

	}

}