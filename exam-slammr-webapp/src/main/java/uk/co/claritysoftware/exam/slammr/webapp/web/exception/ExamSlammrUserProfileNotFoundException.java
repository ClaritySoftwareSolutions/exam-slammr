package uk.co.claritysoftware.exam.slammr.webapp.web.exception;

import uk.co.claritysoftware.exam.slammr.webapp.service.model.user.ExamSlammrUserProfile;

/**
 * RuntimeException for when an {@link ExamSlammrUserProfile} can not be found
 */
public class ExamSlammrUserProfileNotFoundException extends RuntimeException {

	public ExamSlammrUserProfileNotFoundException(String userId) {
		super("ExamSlammrUserProfile with id " + userId + " not found");
	}
}
