package uk.co.claritysoftware.exam.slammr.rest.user.web.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.claritysoftware.exam.slammr.rest.user.testsupport.web.model.UserRegistrationRequestTestDataFactory.smithersUserRegistrationRequest;

/**
 * Unit test class for {@link UserRegistrationRequest}
 */
public class UserRegistrationRequestTest {

    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void shouldValidateGivenValidFields() {
        // Given
        UserRegistrationRequest userRegistrationRequest = smithersUserRegistrationRequest()
                .build();

        // When
        Set<ConstraintViolation<UserRegistrationRequest>> violations = validator.validate(userRegistrationRequest);

        // Then
        assertThat(violations).isEmpty();
    }

    @Test
    public void shouldValidateGivenNullProfilePictureUrlAndNicknameFields() {
        // Given
        UserRegistrationRequest userRegistrationRequest = smithersUserRegistrationRequest()
                .nickname(null)
                .profilePictureUrl(null)
                .build();

        // When
        Set<ConstraintViolation<UserRegistrationRequest>> violations = validator.validate(userRegistrationRequest);

        // Then
        assertThat(violations).isEmpty();
    }

    @Test
    public void shouldNotValidateGivenFirstnameFieldInError() {
        // Given
        UserRegistrationRequest userRegistrationRequest = smithersUserRegistrationRequest()
                .firstname(null)
                .build();

        // When
        Set<ConstraintViolation<UserRegistrationRequest>> violations = validator.validate(userRegistrationRequest);

        // Then
        assertThat(violations).hasSize(1);
        ConstraintViolation violation = violations.toArray(new ConstraintViolation[0])[0];
        assertThat(violation.getPropertyPath().toString()).isEqualTo("firstname");
    }

    @Test
    public void shouldNotValidateGivenSurnameFieldInError() {
        // Given
        UserRegistrationRequest userRegistrationRequest = smithersUserRegistrationRequest()
                .surname(null)
                .build();

        // When
        Set<ConstraintViolation<UserRegistrationRequest>> violations = validator.validate(userRegistrationRequest);

        // Then
        assertThat(violations).hasSize(1);
        ConstraintViolation violation = violations.toArray(new ConstraintViolation[0])[0];
        assertThat(violation.getPropertyPath().toString()).isEqualTo("surname");
    }

    @Test
    public void shouldNotValidateGivenEmailFieldInError() {
        // Given
        UserRegistrationRequest userRegistrationRequest = smithersUserRegistrationRequest()
                .email("not a valid email address")
                .build();

        // When
        Set<ConstraintViolation<UserRegistrationRequest>> violations = validator.validate(userRegistrationRequest);

        // Then
        assertThat(violations).hasSize(1);
        ConstraintViolation violation = violations.toArray(new ConstraintViolation[0])[0];
        assertThat(violation.getPropertyPath().toString()).isEqualTo("email");
    }

    @Test
    public void shouldHonourEqualsHashcodeContract() {
        EqualsVerifier.forClass(UserRegistrationRequest.class)
                .verify();
    }

}