package uk.co.claritysoftware.exam.slammr.user.rest.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import uk.co.claritysoftware.exam.slammr.service.UserService;
import uk.co.claritysoftware.exam.slammr.user.rest.model.UserProfile;
import uk.co.claritysoftware.exam.slammr.user.rest.model.UserRegistrationRequest;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static uk.co.claritysoftware.exam.slammr.user.rest.controller.UserController.BASE_PATH;

/**
 * REST Controller for User concerns
 */
@Slf4j
@RestController
@RequestMapping(value = BASE_PATH, headers = {"x-amz-cognitoIdentityId"})
public class UserController {

    static final String BASE_PATH = "/user";

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public ResponseEntity<Void> registerNewUser(@Valid @RequestBody UserRegistrationRequest userRegistrationRequest,
                                                @RequestHeader("x-amz-cognitoIdentityId") String identityId) {
        log.debug("Register New User with UserRegistrationRequest {} with identityId {}", userRegistrationRequest, identityId);

        return null;
    }

    @GetMapping
    @ResponseStatus(OK)
    public UserProfile getUserProfile(@RequestHeader("x-amz-cognitoIdentityId") String identityId) {
        log.debug("Get UserProfile with identityId {}", identityId);

        return null;
    }
}
