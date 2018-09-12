package uk.co.claritysoftware.exam.slammr.webapp.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller to handle home page requests
 */
@Controller
@RequestMapping("/")
public class HomePageController {

	@GetMapping
	public String getHomePage() {
		return "home";
	}
}
