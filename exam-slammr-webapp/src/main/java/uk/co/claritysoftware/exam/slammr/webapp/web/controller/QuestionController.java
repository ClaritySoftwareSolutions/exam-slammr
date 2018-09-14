package uk.co.claritysoftware.exam.slammr.webapp.web.controller;

import static uk.co.claritysoftware.exam.slammr.webapp.web.model.question.AnswerOption.generateEmptyAnswerOption;
import static uk.co.claritysoftware.exam.slammr.webapp.web.model.question.CreateQuestion.generateEmptyCreateQuestion;
import static uk.co.claritysoftware.exam.slammr.webapp.web.model.question.FurtherReading.generateEmptyFurtherReading;

import java.security.Principal;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.co.claritysoftware.exam.slammr.webapp.web.model.question.CreateQuestion;

/**
 * Controller to handle question page requests
 *
 */
@Controller
@RequestMapping("/question")
public class QuestionController {

	@GetMapping("/new")
	public String getQuestionPage(Principal principal, Model model) {
		if (principal == null) {
			return "redirect:/login";
		}

		model.addAttribute("form", generateEmptyCreateQuestion());
		return "question/create";
	}

	@PostMapping
	public String createNewQuestion(@Valid @ModelAttribute("form") CreateQuestion createQuestion, BindingResult bindingResult, Principal principal) {

		switch(createQuestion.getAction()) {
			case addCertification:
				createQuestion.getCertifications().add("");
				return "question/create";

			case addTag:
				createQuestion.getTags().add("");
				return "question/create";

			case addFurtherReading:
				createQuestion.getFurtherReadings().add(generateEmptyFurtherReading());
				return "question/create";

			case addAnswer:
				createQuestion.getAnswerOptions().add(generateEmptyAnswerOption());
				return "question/create";

			default:
				if (bindingResult.hasErrors()) {
					return "question/create";
				}
		}
return "ss";
	}
}
