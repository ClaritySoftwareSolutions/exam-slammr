package uk.co.claritysoftware.exam.slammr.webapp.web.model.question;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit test class for {@link FurtherReading}
 */
public class FurtherReadingTest {

    @Test
    public void shouldGenerateEmptyFurtherReading() {
        // Given
        FurtherReading expectedFurtherReading = FurtherReading.builder()
                .description(null)
                .referenceLocation(null)
                .build();

        // When
        FurtherReading furtherReading = FurtherReading.generateEmptyFurtherReading();

        // Then
        assertThat(furtherReading).isEqualTo(expectedFurtherReading);
    }

    @Test
    public void shouldHonourEqualsHashcodeContract() {
        EqualsVerifier.forClass(FurtherReading.class)
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();
    }

}