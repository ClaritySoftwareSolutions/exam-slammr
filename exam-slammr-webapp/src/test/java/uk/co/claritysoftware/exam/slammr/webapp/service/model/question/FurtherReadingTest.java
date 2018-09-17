package uk.co.claritysoftware.exam.slammr.webapp.service.model.question;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import uk.co.claritysoftware.exam.slammr.webapp.service.model.question.FurtherReading;

/**
 * Unit test class for {@link FurtherReading}
 */
public class FurtherReadingTest {

	@Test
	public void shouldHonourEqualsHashcodeContract() {
		EqualsVerifier.forClass(FurtherReading.class)
				.verify();
	}
}