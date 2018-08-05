package uk.co.claritysoftware.exam.slammr.rest.user.web.exception;

/**
 * Runtime exception for when a user profile was not found
 */
public class UserProfileNotFoundException extends RuntimeException {

    public UserProfileNotFoundException(String identityId) {
        super(String.format("UserProfile with id %s not found", identityId));
    }
}
