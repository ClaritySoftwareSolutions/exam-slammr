package uk.co.claritysoftware.exam.slammr.rest.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import uk.co.claritysoftware.exam.slammr.rest.model.UserRegistrationRequest;
import uk.co.claritysoftware.exam.slammr.service.UserService;

import javax.validation.Valid;
import java.security.Principal;

import static uk.co.claritysoftware.exam.slammr.rest.controller.UserController.BASE_PATH;

/**
 * REST Controller for User concerns
 */
@Slf4j
@RestController(BASE_PATH)
public class UserController {

    static final String BASE_PATH = "/user";

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> registerNewUser(@Valid @RequestBody UserRegistrationRequest userRegistrationRequest, Principal userPrincipal) {
        if (userPrincipal == null) {
            throw new AccessDeniedException("User Principal must not be null");
        }

        String identityId = userPrincipal.getName();
        log.debug("Register New User with UserRegistrationRequest {} with identityId {}", userRegistrationRequest, identityId);

        return null;
    }
}
