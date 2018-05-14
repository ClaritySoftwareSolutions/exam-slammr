package uk.co.claritysoftware.exam.slammr.rest.controller;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.google.common.base.Preconditions;
import uk.co.claritysoftware.exam.slammr.rest.model.UserRegistrationRequest;
import uk.co.claritysoftware.exam.slammr.service.UserService;

import lombok.extern.slf4j.Slf4j;

/**
 * REST Controller for User concerns
 */
@Slf4j
@RestController("/user")
public class UserController {

	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Void> registerNewUser(UserRegistrationRequest userRegistrationRequest, Principal userPrincipal) {
		Preconditions.checkArgument(userPrincipal != null, "User Principal must not be null");

		String identityId = userPrincipal.getName();
		log.debug("Register New User with UserRegistrationRequest {} with identityId {}", userRegistrationRequest, identityId);

		return null;
	}
}
