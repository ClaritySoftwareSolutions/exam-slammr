package uk.co.claritysoftware.exam.slammr.webapp.web.model.question;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

/**
 * Unit test class for {@link FurtherReading}
 */
public class FurtherReadingTest {

    @Test
    public void shouldHonourEqualsHashcodeContract() {
        EqualsVerifier.forClass(FurtherReading.class)
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();
    }

}