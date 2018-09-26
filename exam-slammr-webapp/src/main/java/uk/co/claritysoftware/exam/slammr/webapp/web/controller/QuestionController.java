package uk.co.claritysoftware.exam.slammr.webapp.web.controller;

import static uk.co.claritysoftware.exam.slammr.webapp.web.factory.CreateQuestionFactory.valueOf;
import static uk.co.claritysoftware.exam.slammr.webapp.web.model.question.AnswerOption.generateEmptyAnswerOption;
import static uk.co.claritysoftware.exam.slammr.webapp.web.model.question.CreateQuestion.generateEmptyCreateQuestion;
import static uk.co.claritysoftware.exam.slammr.webapp.web.model.question.FurtherReading.generateEmptyFurtherReading;

import java.security.Principal;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import uk.co.claritysoftware.exam.slammr.webapp.service.QuestionService;
import uk.co.claritysoftware.exam.slammr.webapp.service.model.question.Question;
import uk.co.claritysoftware.exam.slammr.webapp.web.exception.QuestionNotFoundException;
import uk.co.claritysoftware.exam.slammr.webapp.web.model.question.CreateQuestion;

import lombok.extern.slf4j.Slf4j;

/**
 * Controller to handle question page requests
 */
@Slf4j
@Controller
@RequestMapping("/question")
public class QuestionController {

    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/new")
    public ModelAndView getQuestionPage() {
        return createQuestionViewHidingBindErrors(generateEmptyCreateQuestion());
    }

    @PostMapping
    public ModelAndView createNewQuestion(@Valid @ModelAttribute("form") CreateQuestion form, BindingResult bindingResult, Principal principal) {

        switch (form.getAction()) {
            case addCertification:
                form.getCertifications().add("");
                return createQuestionViewHidingBindErrors(form);

            case addTag:
                form.getTags().add("");
                return createQuestionViewHidingBindErrors(form);

            case addFurtherReading:
                form.getFurtherReadings().add(generateEmptyFurtherReading());
                return createQuestionViewHidingBindErrors(form);

            case addAnswer:
                form.getAnswerOptions().add(generateEmptyAnswerOption());
                return createQuestionViewHidingBindErrors(form);

            default:
                if (bindingResult.hasErrors()) {
                    return createQuestionViewShowingBindErrors(form);
                }

                Question newQuestion = valueOf(form, principal.getName());
                Question savedQuestion = questionService.saveNewQuestion(newQuestion);

                return new ModelAndView("redirect:/question/" + savedQuestion.getId() + "/" + savedQuestion.getSlug());
        }
    }

    @GetMapping("/{id}/{slug}")
	public ModelAndView getExistingQuestionPage(@PathVariable("id") String id, @PathVariable("slug") String slug) {
    	Optional<Question> retrievedQuestion = questionService.getQuestionById(id);
    	if (!retrievedQuestion.isPresent()) {
    		log.warn("Question with id {} could not be found", id);
    		throw new QuestionNotFoundException(id);
		}

		Question question = retrievedQuestion.get();

		if (!question.getSlug().equals(slug)) {
			String redirectUrl = "redirect:/question/" + id + "/" + question.getSlug();
			log.warn("Question with id {} does not have requested slug {}. Redirecting to {}", id, slug, redirectUrl);
			return new ModelAndView(redirectUrl);
		}

		ModelAndView modelAndView = new ModelAndView("question/view");
		modelAndView.addObject("question", question);
    	return modelAndView;
	}

    @GetMapping("/{id}")
	public ModelAndView getExistingQuestionPageWithoutSlug(@PathVariable("id") String id) {
    	return getExistingQuestionPage(id, null);
	}

    private ModelAndView createQuestionViewShowingBindErrors(CreateQuestion form) {
        ModelAndView modelAndView = new ModelAndView("question/create");
        modelAndView.addObject("showBindErrors", true);
		modelAndView.addObject("form", form);
        return modelAndView;
    }

    private ModelAndView createQuestionViewHidingBindErrors(CreateQuestion form) {
        ModelAndView modelAndView = new ModelAndView("question/create");
        modelAndView.addObject("showBindErrors", false);
        modelAndView.addObject("form", form);
        return modelAndView;
    }
}
