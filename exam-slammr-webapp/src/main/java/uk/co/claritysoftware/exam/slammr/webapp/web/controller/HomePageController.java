package uk.co.claritysoftware.exam.slammr.webapp.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import uk.co.claritysoftware.exam.slammr.webapp.web.config.SocialProviders;

/**
 * Controller to handle home page requests
 */
@Controller
@RequestMapping("/")
public class HomePageController {

	private final SocialProviders socialProviders;

	@Autowired
	public HomePageController(SocialProviders socialProviders) {
		this.socialProviders = socialProviders;
	}

	@GetMapping
	public ModelAndView getHomePage() {
		return new ModelAndView("home")
				.addObject("facebookConfigured", socialProviders.isFacebookConfigured())
				.addObject("twitterConfigured", socialProviders.isTwitterConfigured())
				.addObject("linkedInConfigured", socialProviders.isLinkedinConfigured());
	}
}
