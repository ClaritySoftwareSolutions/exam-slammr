package uk.co.claritysoftware.exam.slammr.rest.question.web.contstraintvalidator;

import org.junit.Test;
import uk.co.claritysoftware.exam.slammr.rest.question.web.model.EditableQuestion;

import javax.validation.ConstraintValidatorContext;

import static com.google.common.collect.Sets.newHashSet;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptySet;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit test class for {@link QuestionHasAnswersConstraintValidator}
 */
public class QuestionHasAnswersConstraintValidatorTest {

    private QuestionHasAnswersConstraintValidator validator = new QuestionHasAnswersConstraintValidator();

    private ConstraintValidatorContext context = null;

    @Test
    public void shouldValidateGivenValidQuestionAnswers() {
        // Given
        EditableQuestion question = EditableQuestion.builder()
                .answers(asList("red", "green", "blue"))
                .correctAnswers(newHashSet(2))
                .build();

        // When
        boolean valid = validator.isValid(question, context);

        // Then
        assertThat(valid).isTrue();
    }

    @Test
    public void shouldNotValidateGivenNoCorrectAnswers() {
        // Given
        EditableQuestion question = EditableQuestion.builder()
                .answers(asList("red", "green", "blue"))
                .correctAnswers(emptySet())
                .build();

        // When
        boolean valid = validator.isValid(question, context);

        // Then
        assertThat(valid).isFalse();
    }

    @Test
    public void shouldNotValidateGivenMoreCorrectAnswersThanAnswers() {
        // Given
        EditableQuestion question = EditableQuestion.builder()
                .answers(asList("red", "green", "blue"))
                .correctAnswers(newHashSet(1, 2, 3, 4, 5))
                .build();

        // When
        boolean valid = validator.isValid(question, context);

        // Then
        assertThat(valid).isFalse();
    }

    @Test
    public void shouldNotValidateGivenCorrectAnswersHigherThanTotalAnswers() {
        // Given
        EditableQuestion question = EditableQuestion.builder()
                .answers(asList("red", "green", "blue"))
                .correctAnswers(newHashSet(1, 5))
                .build();

        // When
        boolean valid = validator.isValid(question, context);

        // Then
        assertThat(valid).isFalse();
    }

    @Test
    public void shouldNotValidateGivenCorrectAnswersLowerThan1() {
        // Given
        EditableQuestion question = EditableQuestion.builder()
                .answers(asList("red", "green", "blue"))
                .correctAnswers(newHashSet(0, 2))
                .build();

        // When
        boolean valid = validator.isValid(question, context);

        // Then
        assertThat(valid).isFalse();
    }

    @Test
    public void shouldNotValidateGivenNullAnswers() {
        // Given
        EditableQuestion question = EditableQuestion.builder()
                .answers(null)
                .correctAnswers(newHashSet(0, 2))
                .build();

        // When
        boolean valid = validator.isValid(question, context);

        // Then
        assertThat(valid).isFalse();
    }

    @Test
    public void shouldNotValidateGivenNullCorrectAnswers() {
        // Given
        EditableQuestion question = EditableQuestion.builder()
                .answers(asList("red", "green", "blue"))
                .correctAnswers(null)
                .build();

        // When
        boolean valid = validator.isValid(question, context);

        // Then
        assertThat(valid).isFalse();
    }

    @Test
    public void shouldNotValidateGivenNoAnswers() {
        // Given
        EditableQuestion question = EditableQuestion.builder()
                .answers(emptyList())
                .correctAnswers(newHashSet(0, 2))
                .build();

        // When
        boolean valid = validator.isValid(question, context);

        // Then
        assertThat(valid).isFalse();
    }

}