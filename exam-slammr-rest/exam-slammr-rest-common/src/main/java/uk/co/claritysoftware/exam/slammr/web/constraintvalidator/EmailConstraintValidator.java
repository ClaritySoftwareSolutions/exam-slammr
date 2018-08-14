package uk.co.claritysoftware.exam.slammr.web.constraintvalidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * An implementation of {@link ConstraintValidator} that validates that a given string is a valid email address.
 * <p>
 * Using the annotation {@link Email} it is therefore possible to simply annotate a field as follows:
 * <pre>
 * <code>
 *
 *     public class SomeDtoClass {
 *
 *        {@literal @}Email(maximumLength = 64)
 *         private String email;
 *
 *         ...
 *     }
 *
 * </code>
 * </pre>
 */
public class EmailConstraintValidator implements ConstraintValidator<Email, String> {

    private boolean required;

    private int maximumFieldLength;

    private String regexp;

    @Override
    public void initialize(Email email) {
        this.required = email.required();
        this.maximumFieldLength = email.maximumLength();
        this.regexp = email.regexp();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (required && isBlank(value)) {
            return false;
        }
        if (isBlank(value)) {
            return true;
        }
        if (!regexp.equals("") && !value.matches(regexp)) {
            return false;
        }
        if (value.length() > maximumFieldLength) {
            return false;
        }
        return true;
    }
}
