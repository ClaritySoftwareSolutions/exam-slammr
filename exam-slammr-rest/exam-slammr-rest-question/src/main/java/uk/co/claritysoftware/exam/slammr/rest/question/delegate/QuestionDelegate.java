package uk.co.claritysoftware.exam.slammr.rest.question.delegate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.co.claritysoftware.exam.slammr.rest.question.exception.QuestionCreateException;
import uk.co.claritysoftware.exam.slammr.rest.question.factory.QuestionItemFactory;
import uk.co.claritysoftware.exam.slammr.rest.question.service.QuestionService;
import uk.co.claritysoftware.exam.slammr.rest.question.service.dynamodb.QuestionItem;
import uk.co.claritysoftware.exam.slammr.rest.question.web.model.QuestionCreateRequest;

/**
 * Delegate class for Question concerns
 */
@Slf4j
@Component
public class QuestionDelegate {

    private final QuestionService questionService;

    private final QuestionItemFactory questionItemFactory;

    @Autowired
    public QuestionDelegate(QuestionService questionService, QuestionItemFactory questionItemFactory) {
        this.questionService = questionService;
        this.questionItemFactory = questionItemFactory;
    }

    /**
     * Saves a new Question and returns it's id
     *
     * @param questionCreateRequest the new question request from the client to save
     * @param identityId            the identity id of the user creating the new question
     * @return the id of the new question
     */
    public String createQuestion(QuestionCreateRequest questionCreateRequest, String identityId) {
        log.debug("Create Question with request {}", questionCreateRequest);

        QuestionItem newQuestionItem = questionItemFactory.valueOf(questionCreateRequest, identityId);
        return questionService.createQuestion(newQuestionItem)
                .orElseThrow(() -> new QuestionCreateException(questionCreateRequest));
    }
}
