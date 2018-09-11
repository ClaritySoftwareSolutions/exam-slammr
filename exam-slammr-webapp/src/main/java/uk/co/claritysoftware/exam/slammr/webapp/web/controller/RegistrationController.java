package uk.co.claritysoftware.exam.slammr.webapp.web.controller;

import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

/**
 * Controller for the user registration page
 */
@Slf4j
@Controller
public class RegistrationController {

	@RequestMapping("/register")
	public String getRegistrationPage(Principal principal, Model model) {
		if (principal != null) {
			return "redirect:/";
		}

		return "registration";
	}

}
