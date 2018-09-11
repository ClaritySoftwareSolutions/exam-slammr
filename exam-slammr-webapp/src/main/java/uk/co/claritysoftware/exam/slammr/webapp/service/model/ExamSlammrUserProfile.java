package uk.co.claritysoftware.exam.slammr.webapp.service.model;

import java.time.ZonedDateTime;
import java.util.Set;

import lombok.Builder;
import lombok.Value;

/**
 * Pojo defining an Exam Slammr User Profile
 */
@Value
@Builder
public class ExamSlammrUserProfile {

	private String id;

	private String compositeUserId;

	private String firstname;

	private String surname;

	private String nickname;

	private String email;

	private String profilePictureUrl;

	private Set<String> roles;

	private ZonedDateTime registrationDateTime;

	private ZonedDateTime lastLogonDateTime;

}
