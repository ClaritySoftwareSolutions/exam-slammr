package uk.co.claritysoftware.exam.slammr.webapp.web.controller;

import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller to handle login page requests
 */
@Controller
@RequestMapping("/login")
public class LoginPageController {

	@GetMapping
	public String getLoginPage(Principal principal) {
		if (principal != null) {
			return "redirect:/";
		} else {
			return "auth/login";
		}
	}
}
