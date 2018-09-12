package uk.co.claritysoftware.exam.slammr.webapp.web.controller;

import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller to handle registration page requests
 */
@Controller
@RequestMapping("/register")
public class RegistrationPageController {

	@GetMapping
	public String getRegistrationPage(Principal principal) {
		if (principal != null) {
			return "redirect:/";
		} else {
			return "auth/registration";
		}
	}
}
