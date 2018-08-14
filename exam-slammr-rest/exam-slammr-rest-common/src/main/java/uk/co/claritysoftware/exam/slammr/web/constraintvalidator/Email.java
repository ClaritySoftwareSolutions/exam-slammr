package uk.co.claritysoftware.exam.slammr.web.constraintvalidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * {@link Constraint} annotation that uses {@link EmailConstraintValidator} as it's implementation to allow class fields
 * to be annotated and validated as containing a valid email address, as follows:
 * <pre>
 * <code>
 *
 *     public class SomeClass {
 *
 *        {@literal @}Email(required = true, maximumLength = 64)
 *         private String email;
 *
 *         ...
 *     }
 *
 * </code>
 * </pre>
 * The annotation attributes are:
 * <ul>
 *     <li>message - the message when constraint violated. Defaults to {uk.co.claritysoftware.exam.slammr.web.constraintvalidator.Email}. Optional attribute</li>
 *     <li>required - whether the email address field is mandatory. Defaults to false. Optional attribute</li>
 *     <li>maximumLength - the maximum length of an email address. Defaults to Integer.MAX_VALUE. Optional attribute</li>
 *     <li>regexp - the regular expression to validate an email address. Defaults to the W3C HTML5 pattern as implemented in browsers. Optional attribute</li>
 * </ul>
 */
@Target({FIELD, PARAMETER, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = EmailConstraintValidator.class)
public @interface Email {

    String W3C_HTML5_EMAIL_PATTERN = "^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";

    String message() default "{uk.co.claritysoftware.exam.slammr.web.constraintvalidator.Email.message}";

    boolean required() default false;

    String regexp() default W3C_HTML5_EMAIL_PATTERN;

    int maximumLength() default Integer.MAX_VALUE;

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
