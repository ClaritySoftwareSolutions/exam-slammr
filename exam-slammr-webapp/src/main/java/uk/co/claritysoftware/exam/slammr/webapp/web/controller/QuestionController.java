package uk.co.claritysoftware.exam.slammr.webapp.web.controller;

import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller to handle question page requests
 *
 */
@Controller
@RequestMapping("/question")
public class QuestionController {

	@GetMapping("/new")
	public String getQuestionPage(Principal principal) {
		if (principal == null) {
			return "redirect:/login";
		}

		return "question/create";
	}

}
