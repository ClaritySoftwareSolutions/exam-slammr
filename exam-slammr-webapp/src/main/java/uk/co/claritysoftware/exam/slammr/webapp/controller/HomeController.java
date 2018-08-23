package uk.co.claritysoftware.exam.slammr.webapp.controller;

import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for the site homepage
 */
@Controller
public class HomeController {

	@RequestMapping("/")
	public String getHomepage(Principal currentUser, Model model) {
		return "home";
	}
}
