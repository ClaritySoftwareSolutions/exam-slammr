package uk.co.claritysoftware.exam.slammr.user.rest.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import uk.co.claritysoftware.exam.slammr.user.rest.exception.UserProfileNotFoundException;
import uk.co.claritysoftware.exam.slammr.user.rest.factory.UserProfileFactory;
import uk.co.claritysoftware.exam.slammr.user.rest.model.UserProfile;
import uk.co.claritysoftware.exam.slammr.user.service.UserProfileService;

import java.security.Principal;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static uk.co.claritysoftware.exam.slammr.user.rest.controller.UserController.BASE_PATH;

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
    public ResponseEntity<Void> registerNewUser(//@Valid @RequestBody UserRegistrationRequest userRegistrationRequest,
    ) {
        // log.debug("Register New User with UserRegistrationRequest {} with identityId {}", userRegistrationRequest, identityId);

        return null;
    }

    @GetMapping
    @ResponseStatus(OK)
    public UserProfile getUserProfile(Principal userPrincipal) {
        String identityId = userPrincipal.getName();
        log.debug("Get UserProfile with identityId {}", identityId);

        return userProfileFactory.valueOf(userProfileService.getUserProfile(identityId)
                .orElseThrow(() -> new UserProfileNotFoundException(identityId)));
    }
}
