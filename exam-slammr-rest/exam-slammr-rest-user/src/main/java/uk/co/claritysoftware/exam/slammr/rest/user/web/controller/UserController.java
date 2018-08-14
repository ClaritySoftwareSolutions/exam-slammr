package uk.co.claritysoftware.exam.slammr.rest.user.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import uk.co.claritysoftware.exam.slammr.rest.user.delegate.UserDelegate;
import uk.co.claritysoftware.exam.slammr.rest.user.web.model.UserProfile;
import uk.co.claritysoftware.exam.slammr.rest.user.web.model.UserRegistrationRequest;

import javax.validation.Valid;
import java.security.Principal;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static uk.co.claritysoftware.exam.slammr.rest.user.web.controller.UserController.BASE_PATH;

/**
 * REST Controller for User concerns
 */
@Slf4j
@RestController
@RequestMapping(value = BASE_PATH)
public class UserController {

    static final String BASE_PATH = "/user";

    private final UserDelegate userDelegate;

    @Autowired
    public UserController(UserDelegate userDelegate) {
        this.userDelegate = userDelegate;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public void registerNewUser(@Valid @RequestBody UserRegistrationRequest userRegistrationRequest, Principal userPrincipal) {
        String identityId = userPrincipal.getName();
        log.debug("Register New User with UserRegistrationRequest {}", userRegistrationRequest);
        userDelegate.registerUserProfile(userRegistrationRequest, identityId);
    }

    @GetMapping
    @ResponseStatus(OK)
    public UserProfile getUserProfile(Principal userPrincipal) {
        log.debug("Get UserProfile");
        String identityId = userPrincipal.getName();
        return userDelegate.getUserProfile(identityId);
    }
}
