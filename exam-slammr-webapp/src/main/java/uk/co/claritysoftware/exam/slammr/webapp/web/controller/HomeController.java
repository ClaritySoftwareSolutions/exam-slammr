package uk.co.claritysoftware.exam.slammr.webapp.web.controller;

import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

/**
 * Controller for the site homepage
 */
@Slf4j
@Controller
public class HomeController {

	@RequestMapping("/")
	public String getHomepage(Principal currentUser, Model model) {

		return "home";
	}
}
