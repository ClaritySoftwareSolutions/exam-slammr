package uk.co.claritysoftware.exam.slammr.rest.question.web.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static uk.co.claritysoftware.exam.slammr.rest.question.web.controller.QuestionController.BASE_PATH;

import java.security.Principal;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import uk.co.claritysoftware.exam.slammr.rest.question.delegate.QuestionDelegate;
import uk.co.claritysoftware.exam.slammr.rest.question.web.model.EditableQuestion;
import uk.co.claritysoftware.exam.slammr.rest.question.web.model.Question;

import lombok.extern.slf4j.Slf4j;

/**
 * REST Controller for User concerns
 */
@Slf4j
@RestController
@RequestMapping(value = BASE_PATH)
public class QuestionController {

    static final String BASE_PATH = "/question";

    private final QuestionDelegate questionDelegate;

    @Autowired
    public QuestionController(QuestionDelegate questionDelegate) {
        this.questionDelegate = questionDelegate;
    }

    @GetMapping
	@ResponseStatus(OK)
	@RequestMapping("/{questionId}")
	public Question getQuestion(@PathVariable("questionId") String questionId) {
		log.debug("Get Question with id {}", questionId);

		return questionDelegate.getQuestion(questionId);
	}

    @PostMapping
    @ResponseStatus(CREATED)
    public void createNewQuestion(@Valid @RequestBody EditableQuestion editableQuestion, Principal userPrincipal) {
        String identityId = userPrincipal.getName();
        log.debug("Create new Question with EditableQuestion {}", editableQuestion);
        questionDelegate.createQuestion(editableQuestion, identityId);
    }
}
