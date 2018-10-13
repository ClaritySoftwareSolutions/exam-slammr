package uk.co.claritysoftware.exam.slammr.webapp.web.controller;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import uk.co.claritysoftware.exam.slammr.webapp.web.config.SocialProviders;

/**
 * Controller to handle login page requests
 */
@Controller
@RequestMapping("/login")
public class LoginPageController {

	private final SocialProviders socialProviders;

	@Autowired
	public LoginPageController(SocialProviders socialProviders) {
		this.socialProviders = socialProviders;
	}

	@GetMapping
	public ModelAndView getLoginPage(Principal principal) {
		if (principal != null) {
			return new ModelAndView("redirect:/");
		} else {
			return new ModelAndView("auth/login")
					.addObject("facebookConfigured", socialProviders.isFacebookConfigured())
					.addObject("twitterConfigured", socialProviders.isTwitterConfigured())
					.addObject("linkedInConfigured", socialProviders.isLinkedinConfigured());
		}
	}
}
