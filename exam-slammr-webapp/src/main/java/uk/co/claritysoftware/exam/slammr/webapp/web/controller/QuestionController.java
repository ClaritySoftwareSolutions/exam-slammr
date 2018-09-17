package uk.co.claritysoftware.exam.slammr.webapp.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import uk.co.claritysoftware.exam.slammr.webapp.service.QuestionService;
import uk.co.claritysoftware.exam.slammr.webapp.service.model.question.Question;
import uk.co.claritysoftware.exam.slammr.webapp.web.model.question.CreateQuestion;

import javax.validation.Valid;
import java.security.Principal;

import static uk.co.claritysoftware.exam.slammr.webapp.web.factory.CreateQuestionFactory.valueOf;
import static uk.co.claritysoftware.exam.slammr.webapp.web.model.question.AnswerOption.generateEmptyAnswerOption;
import static uk.co.claritysoftware.exam.slammr.webapp.web.model.question.CreateQuestion.generateEmptyCreateQuestion;
import static uk.co.claritysoftware.exam.slammr.webapp.web.model.question.FurtherReading.generateEmptyFurtherReading;

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
    public ModelAndView getQuestionPage(Principal principal, Model model) {
        if (principal == null) {
            return new ModelAndView("redirect:/login");
        }

        model.addAttribute("form", generateEmptyCreateQuestion());
        return createQuestionViewHidingBindErrors();
    }

    @PostMapping
    public ModelAndView createNewQuestion(@Valid @ModelAttribute("form") CreateQuestion createQuestion, BindingResult bindingResult, Model model, Principal principal) {

        switch (createQuestion.getAction()) {
            case addCertification:
                createQuestion.getCertifications().add("");
                return createQuestionViewHidingBindErrors();

            case addTag:
                createQuestion.getTags().add("");
                return createQuestionViewHidingBindErrors();

            case addFurtherReading:
                createQuestion.getFurtherReadings().add(generateEmptyFurtherReading());
                return createQuestionViewHidingBindErrors();

            case addAnswer:
                createQuestion.getAnswerOptions().add(generateEmptyAnswerOption());
                return createQuestionViewHidingBindErrors();

            default:
                if (bindingResult.hasErrors()) {
                    return createQuestionViewShowingBindErrors();
                }

                Question newQuestion = valueOf(createQuestion, principal.getName());
                Question savedQuestion = questionService.saveNewQuestion(newQuestion);

                return new ModelAndView("/");
        }
    }

    private ModelAndView createQuestionViewShowingBindErrors() {
        ModelAndView modelAndView = new ModelAndView("question/create");
        modelAndView.addObject("showBindErrors", true);
        return modelAndView;
    }

    private ModelAndView createQuestionViewHidingBindErrors() {
        ModelAndView modelAndView = new ModelAndView("question/create");
        modelAndView.addObject("showBindErrors", false);
        return modelAndView;
    }
}
