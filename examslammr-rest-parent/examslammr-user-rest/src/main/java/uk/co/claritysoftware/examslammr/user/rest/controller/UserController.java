package uk.co.claritysoftware.examslammr.user.rest.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static uk.co.claritysoftware.examslammr.user.rest.controller.UserController.BASE_PATH;

import java.security.Principal;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import uk.co.claritysoftware.examslammr.user.rest.exception.UserProfileNotFoundException;
import uk.co.claritysoftware.examslammr.user.rest.factory.UserProfileFactory;
import uk.co.claritysoftware.examslammr.user.rest.model.UserProfile;
import uk.co.claritysoftware.examslammr.user.rest.model.UserRegistrationRequest;
import uk.co.claritysoftware.examslammr.user.service.UserProfileService;

import lombok.extern.slf4j.Slf4j;

/**
 * REST Controller for User concerns
 */
@Slf4j
@RestController
@RequestMapping(value = BASE_PATH)
public class UserController {

    static final String BASE_PATH = "/user";

    private final UserProfileService userProfileService;

    private final UserProfileFactory userProfileFactory;

    @Autowired
    public UserController(UserProfileService userProfileService, UserProfileFactory userProfileFactory) {
        this.userProfileService = userProfileService;
        this.userProfileFactory = userProfileFactory;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public void registerNewUser(@Valid @RequestBody UserRegistrationRequest userRegistrationRequest, Principal userPrincipal) {
        String identityId = userPrincipal.getName();
        log.debug("Register New User with UserRegistrationRequest {}", userRegistrationRequest);

        userProfileService.registerUserProfile(userRegistrationRequest, identityId);
    }

    @GetMapping
    @ResponseStatus(OK)
    public UserProfile getUserProfile(Principal userPrincipal) {
        String identityId = userPrincipal.getName();
        log.debug("Get UserProfile");

        return userProfileFactory.valueOf(userProfileService.getUserProfile(identityId)
                .orElseThrow(() -> new UserProfileNotFoundException(identityId)));
    }
}
