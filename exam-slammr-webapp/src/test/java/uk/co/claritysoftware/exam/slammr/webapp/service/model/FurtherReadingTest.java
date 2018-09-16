package uk.co.claritysoftware.exam.slammr.webapp.service.model;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

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