package uk.co.claritysoftware.exam.slammr.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import uk.co.claritysoftware.exam.slammr.rest.model.UserRegistrationRequest;
import uk.co.claritysoftware.exam.slammr.service.UserService;

/**
 * REST Controller for User concerns
 */
@RestController("/user")
public class UserController {

	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Void> registerNewUser(UserRegistrationRequest userRegistrationRequest) {
		return null;
	}
}
