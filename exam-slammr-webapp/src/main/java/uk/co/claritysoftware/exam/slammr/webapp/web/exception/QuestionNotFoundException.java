package uk.co.claritysoftware.exam.slammr.webapp.web.exception;

/**
 * RuntimeException for when a Question can not be found
 */
public class QuestionNotFoundException extends RuntimeException {

	public QuestionNotFoundException(String questionId) {
		super("Question with id " + questionId + " not found");
	}
}
