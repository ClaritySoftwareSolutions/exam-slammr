package uk.co.claritysoftware.exam.slammr.webapp.web.controller

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView
import uk.co.claritysoftware.exam.slammr.webapp.web.config.SocialProviders
import java.security.Principal

/**
 * Controller to handle login page requests
 */
@Controller
@RequestMapping("/login")
class LoginPageController(private val socialProviders: SocialProviders) {

	private companion object {
		val log = LoggerFactory.getLogger(LoginPageController::class.java)
	}

	@GetMapping
	fun getLoginPage(principal: Principal?): ModelAndView {

		return principal?.let {
			ModelAndView("redirect:/")
		}
				?: ModelAndView("auth/login")
						.addObject("facebookConfigured", socialProviders.isFacebookConfigured)
						.addObject("twitterConfigured", socialProviders.isTwitterConfigured)
						.addObject("linkedInConfigured", socialProviders.isLinkedinConfigured)
	}

}