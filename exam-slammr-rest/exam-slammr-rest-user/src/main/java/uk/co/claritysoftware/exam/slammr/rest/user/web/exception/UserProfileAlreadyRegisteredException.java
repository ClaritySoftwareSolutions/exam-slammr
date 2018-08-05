package uk.co.claritysoftware.exam.slammr.rest.user.web.exception;

/**
 * Runtime exception for when an attempt to register a user profile that already exists
 */
public class UserProfileAlreadyRegisteredException extends RuntimeException {

    public UserProfileAlreadyRegisteredException(String identityId) {
        super(String.format("UserProfile id %s already registered", identityId));
    }
}
