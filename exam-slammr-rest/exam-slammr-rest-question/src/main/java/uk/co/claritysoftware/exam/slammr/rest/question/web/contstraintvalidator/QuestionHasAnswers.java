package uk.co.claritysoftware.exam.slammr.rest.question.web.contstraintvalidator;

import uk.co.claritysoftware.exam.slammr.rest.question.web.model.EditableQuestion;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * {@link Constraint} annotation that uses {@link QuestionHasAnswersConstraintValidator} as it's implementation to validate
 * a {@link EditableQuestion} has more answer choices than correct answers, and that all of the correct answers are for valid questions.
 */
@Target({TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = QuestionHasAnswersConstraintValidator.class)
@Documented
public @interface QuestionHasAnswers {

    String message() default "{uk.co.claritysoftware.exam.slammr.rest.question.web.contstraintvalidator.QuestionHasAnswers.message}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
