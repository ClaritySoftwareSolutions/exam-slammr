package uk.co.claritysoftware.exam.slammr.user.rest.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import uk.co.claritysoftware.exam.slammr.user.rest.exception.UserAuthenticationTokenMissingException;
import uk.co.claritysoftware.exam.slammr.user.rest.exception.UserProfileNotFoundException;

import static org.springframework.http.ResponseEntity.status;

/**
 * Custom exception handler
 */
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserAuthenticationTokenMissingException.class)
    public ResponseEntity<Void> handleUserAuthenticationTokenMissingException(UserAuthenticationTokenMissingException e) {
        log.debug("UserAuthenticationTokenMissingException: {}", e.getMessage());
        return status(HttpStatus.UNAUTHORIZED).build();
    }

    @ExceptionHandler(UserProfileNotFoundException.class)
    public ResponseEntity<Void> handleUserProfileNotFoundException(UserProfileNotFoundException e) {
        log.debug("UserProfileNotFoundException: {}", e.getMessage());
        return status(HttpStatus.NOT_FOUND).build();
    }
}
