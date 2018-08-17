package uk.co.claritysoftware.exam.slammr.rest.question.web.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;
import static java.util.Arrays.asList;
import static java.util.Collections.emptySet;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit test class for {@link EditableQuestion}
 */
public class EditableQuestionTest {

    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void shouldValidateGivenValidFields() {
        // Given
        EditableQuestion editableQuestion = EditableQuestion.builder()
                .questionText("What colour is the sky?")
                .answers(asList("red", "blue", "green"))
                .correctAnswers(newHashSet(1))
                .tags(newHashSet("nature", "general knowledge"))
                .certifications(newHashSet("Basic Science"))
                .furtherReadings(newHashSet(
                        FurtherReading.builder()
                                .description("Basic Science 101")
                                .referenceLocation("http://go-science.com")
                                .build()
                ))
                .build();

        // When
        Set<ConstraintViolation<EditableQuestion>> violations = validator.validate(editableQuestion);

        // Then
        assertThat(violations).isEmpty();
    }

    @Test
    public void shouldValidateGivenNullFurtherReadings() {
        // Given
        EditableQuestion editableQuestion = EditableQuestion.builder()
                .questionText("What colour is the sky?")
                .answers(asList("red", "blue", "green"))
                .correctAnswers(newHashSet(1))
                .tags(newHashSet("nature", "general knowledge"))
                .certifications(newHashSet("Basic Science"))
                .furtherReadings(null)
                .build();

        // When
        Set<ConstraintViolation<EditableQuestion>> violations = validator.validate(editableQuestion);

        // Then
        assertThat(violations).isEmpty();
    }

    @Test
    public void shouldNotValidateGivenQuestionTextFieldInError() {
        // Given
        EditableQuestion editableQuestion = EditableQuestion.builder()
                .questionText("   ")
                .answers(asList("red", "blue", "green"))
                .correctAnswers(newHashSet(1))
                .tags(newHashSet("nature", "general knowledge"))
                .certifications(newHashSet("Basic Science"))
                .furtherReadings(null)
                .build();

        // When
        Set<ConstraintViolation<EditableQuestion>> violations = validator.validate(editableQuestion);

        // Then
        assertThat(violations).hasSize(1);
        ConstraintViolation violation = violations.toArray(new ConstraintViolation[0])[0];
        assertThat(violation.getPropertyPath().toString()).isEqualTo("questionText");
    }

    @Test
    public void shouldNotValidateGivenAnswersFieldInError() {
        // Given
        EditableQuestion editableQuestion = EditableQuestion.builder()
                .questionText("What colour is the sky?")
                .answers(null)
                .correctAnswers(newHashSet(1))
                .tags(newHashSet("nature", "general knowledge"))
                .certifications(newHashSet("Basic Science"))
                .furtherReadings(null)
                .build();

        // When
        Set<ConstraintViolation<EditableQuestion>> violations = validator.validate(editableQuestion);

        // Then
        assertThat(violations).hasSize(1);
    }

    @Test
    public void shouldNotValidateGivenCorrectAnswersFieldInError() {
        // Given
        EditableQuestion editableQuestion = EditableQuestion.builder()
                .questionText("What colour is the sky?")
                .answers(asList("red", "blue", "green"))
                .correctAnswers(null)
                .tags(newHashSet("nature", "general knowledge"))
                .certifications(newHashSet("Basic Science"))
                .furtherReadings(null)
                .build();

        // When
        Set<ConstraintViolation<EditableQuestion>> violations = validator.validate(editableQuestion);

        // Then
        assertThat(violations).hasSize(1);
    }

    @Test
    public void shouldNotValidateGivenTagsFieldInError() {
        // Given
        EditableQuestion editableQuestion = EditableQuestion.builder()
                .questionText("What colour is the sky?")
                .answers(asList("red", "blue", "green"))
                .correctAnswers(newHashSet(2))
                .tags(emptySet())
                .certifications(newHashSet("Basic Science"))
                .furtherReadings(null)
                .build();

        // When
        Set<ConstraintViolation<EditableQuestion>> violations = validator.validate(editableQuestion);

        // Then
        assertThat(violations).hasSize(1);
        ConstraintViolation violation = violations.toArray(new ConstraintViolation[0])[0];
        assertThat(violation.getPropertyPath().toString()).isEqualTo("tags");
    }

    @Test
    public void shouldNotValidateGivenCertificationsFieldInError() {
        // Given
        EditableQuestion editableQuestion = EditableQuestion.builder()
                .questionText("What colour is the sky?")
                .answers(asList("red", "blue", "green"))
                .correctAnswers(newHashSet(2))
                .tags(newHashSet("nature", "general knowledge"))
                .certifications(emptySet())
                .furtherReadings(null)
                .build();

        // When
        Set<ConstraintViolation<EditableQuestion>> violations = validator.validate(editableQuestion);

        // Then
        assertThat(violations).hasSize(1);
        ConstraintViolation violation = violations.toArray(new ConstraintViolation[0])[0];
        assertThat(violation.getPropertyPath().toString()).isEqualTo("certifications");
    }

    @Test
    public void shouldHonourEqualsHashcodeContract() {
        EqualsVerifier.forClass(EditableQuestion.class)
                .verify();
    }

}