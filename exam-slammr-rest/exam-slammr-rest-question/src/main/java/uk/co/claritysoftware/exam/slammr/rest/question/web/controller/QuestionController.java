package uk.co.claritysoftware.exam.slammr.rest.question.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import uk.co.claritysoftware.exam.slammr.rest.question.delegate.QuestionDelegate;
import uk.co.claritysoftware.exam.slammr.rest.question.service.QuestionService;
import uk.co.claritysoftware.exam.slammr.rest.question.web.model.QuestionCreateRequest;

import javax.validation.Valid;
import java.security.Principal;

import static org.springframework.http.HttpStatus.CREATED;
import static uk.co.claritysoftware.exam.slammr.rest.question.web.controller.QuestionController.BASE_PATH;

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

    @PostMapping
    @ResponseStatus(CREATED)
    public void createNewQuestion(@Valid @RequestBody QuestionCreateRequest questionCreateRequest, Principal userPrincipal) {
        String identityId = userPrincipal.getName();
        log.debug("Create new Question with QuestionCreateRequest {}", questionCreateRequest);
        questionDelegate.createQuestion(questionCreateRequest, identityId);
    }
}
