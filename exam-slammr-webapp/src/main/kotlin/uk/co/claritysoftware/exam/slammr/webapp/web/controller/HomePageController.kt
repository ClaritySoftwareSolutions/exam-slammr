package uk.co.claritysoftware.exam.slammr.webapp.web.controller

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView
import uk.co.claritysoftware.exam.slammr.webapp.web.config.SocialProviders

/**
 * Controller to handle home page requests
 */
@Controller
@RequestMapping("/")
class HomePageController(private val socialProviders: SocialProviders) {

	private companion object {
		val log = LoggerFactory.getLogger(HomePageController::class.java)
	}

	@GetMapping
	fun getHomePage(): ModelAndView = ModelAndView("home")
			.addObject("facebookConfigured", socialProviders.isFacebookConfigured)
			.addObject("twitterConfigured", socialProviders.isTwitterConfigured)
			.addObject("linkedInConfigured", socialProviders.isLinkedinConfigured)

}