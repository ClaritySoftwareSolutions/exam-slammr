package uk.co.claritysoftware.exam.slammr.webapp.web.model.question;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit test class for {@link AnswerOption}
 */
public class AnswerOptionTest {

    @Test
    public void shouldGenerateEmptyAnswerOption() {
        // Given
        AnswerOption expectedAnswerOption = AnswerOption.builder()
                .answer(null)
                .correct(false)
                .build();

        // When
        AnswerOption answerOption = AnswerOption.generateEmptyAnswerOption();

        // Then
        assertThat(answerOption).isEqualTo(expectedAnswerOption);
    }

    @Test
    public void shouldHonourEqualsHashcodeContract() {
        EqualsVerifier.forClass(AnswerOption.class)
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();
    }

}