package uk.co.claritysoftware.exam.slammr.rest.question.exception;

import uk.co.claritysoftware.exam.slammr.rest.question.web.model.QuestionCreateRequest;

/**
 * Runtime exception for when the creation of a new question failed
 */
public class QuestionCreateException extends RuntimeException {

    public QuestionCreateException(QuestionCreateRequest questionCreateRequest) {
        super(String.format("Exception creating question '%s'", questionCreateRequest.getQuestionText()));
    }
}
