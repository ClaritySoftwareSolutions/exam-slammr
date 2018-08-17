package uk.co.claritysoftware.exam.slammr.rest.question.exception;

import uk.co.claritysoftware.exam.slammr.rest.question.web.model.EditableQuestion;

/**
 * Runtime exception for when the creation of a new question failed
 */
public class QuestionCreateException extends RuntimeException {

    public QuestionCreateException(EditableQuestion editableQuestion) {
        super(String.format("Exception creating question '%s'", editableQuestion.getQuestionText()));
    }
}
