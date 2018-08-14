package uk.co.claritysoftware.exam.slammr.web.constraintvalidator;

import org.junit.Test;

import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit test class for {@link EmailConstraintValidator}
 */
public class EmailConstraintValidatorTest {

    @Email()
    private String optionalEmail;

    @Email(required = true)
    private String requiredEmail;

    @Email(maximumLength = 10)
    private String maxLengthEmail;

    @Email(regexp = "^[A-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[A-Z0-9-]+(?:\\.[A-Z0-9-]+)*$")
    private String upperCaseEmail;

    private EmailConstraintValidator validator;

    private ConstraintValidatorContext context = null;

    @Test
    public void shouldValidateOptionalEmailGivenNull() {
        // Given
        setupValidatorForField("optionalEmail");
        String emailAddress = null;

        // When
        boolean isValid = validator.isValid(emailAddress, context);

        // Then
        assertThat(isValid).isTrue();
    }

    @Test
    public void shouldValidateOptionalEmailGivenEmptyString() {
        // Given
        setupValidatorForField("optionalEmail");
        String emailAddress = "";

        // When
        boolean isValid = validator.isValid(emailAddress, context);

        // Then
        assertThat(isValid).isTrue();
    }

    @Test
    public void shouldValidateOptionalEmail() {
        // Given
        setupValidatorForField("optionalEmail");
        String emailAddress = "someone@nominet.uk";

        // When
        boolean isValid = validator.isValid(emailAddress, context);

        // Then
        assertThat(isValid).isTrue();
    }

    @Test
    public void shouldNotValidateOptionalEmailGivenInvalidEmailAddress() {
        // Given
        setupValidatorForField("optionalEmail");
        String emailAddress = "not a valid email address";

        // When
        boolean isValid = validator.isValid(emailAddress, context);

        // Then
        assertThat(isValid).isFalse();
    }

    @Test
    public void shouldNotValidateRequiredEmailGivenNull() {
        // Given
        setupValidatorForField("requiredEmail");
        String emailAddress = null;

        // When
        boolean isValid = validator.isValid(emailAddress, context);

        // Then
        assertThat(isValid).isFalse();
    }

    @Test
    public void shouldNotValidateRequiredEmailGivenEmptyString() {
        // Given
        setupValidatorForField("requiredEmail");
        String emailAddress = "";

        // When
        boolean isValid = validator.isValid(emailAddress, context);

        // Then
        assertThat(isValid).isFalse();
    }

    @Test
    public void shouldValidateRequiredEmail() {
        // Given
        setupValidatorForField("requiredEmail");
        String emailAddress = "someone@nominet.uk";

        // When
        boolean isValid = validator.isValid(emailAddress, context);

        // Then
        assertThat(isValid).isTrue();
    }

    @Test
    public void shouldNotValidateRequiredEmailGivenInvalidEmailAddress() {
        // Given
        setupValidatorForField("requiredEmail");
        String emailAddress = "not a valid email address";

        // When
        boolean isValid = validator.isValid(emailAddress, context);

        // Then
        assertThat(isValid).isFalse();
    }

    @Test
    public void shouldValidateMaxLengthEmail() {
        // Given
        setupValidatorForField("maxLengthEmail");
        String emailAddress = "a@email.uk";

        // When
        boolean isValid = validator.isValid(emailAddress, context);

        // Then
        assertThat(isValid).isTrue();
    }

    @Test
    public void shouldNotValidateMaxLengthEmailGivenLongEmailAddress() {
        // Given
        setupValidatorForField("maxLengthEmail");
        String emailAddress = "email.address@too.long.com";

        // When
        boolean isValid = validator.isValid(emailAddress, context);

        // Then
        assertThat(isValid).isFalse();
    }

    @Test
    public void shouldNotValidateUppercaseEmailGivenLowerCaseEmail() {
        // Given
        setupValidatorForField("upperCaseEmail");
        String emailAddress = "lower.case.email.address@nominet.uk";

        // When
        boolean isValid = validator.isValid(emailAddress, context);

        // Then
        assertThat(isValid).isFalse();
    }

    @Test
    public void shouldValidateUppercaseEmailGivenUpperCaseEmail() {
        // Given
        setupValidatorForField("upperCaseEmail");
        String emailAddress = "UPPER.CASE.EMAIL.ADDRESS@NOMINET.UK";

        // When
        boolean isValid = validator.isValid(emailAddress, context);

        // Then
        assertThat(isValid).isTrue();
    }

    private void setupValidatorForField(String fieldName) {
        validator = new EmailConstraintValidator();
        Email annotation = getAnnotation(fieldName);
        validator.initialize(annotation);
    }

    private Email getAnnotation(String fieldName) {
        Field[] fields = this.getClass().getDeclaredFields();
        return Arrays.stream(fields)
                .filter(field -> field.getName().equals(fieldName))
                .filter(field -> field.isAnnotationPresent(Email.class))
                .map(field -> field.getAnnotation(Email.class))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Could not find field " + fieldName + " in this class annotated with @Email"));
    }

}