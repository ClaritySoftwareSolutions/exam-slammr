package uk.co.claritysoftware.exam.slammr.webapp.service.model;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

/**
 * Unit test class for {@link AnswerOption}
 */
public class AnswerOptionTest {

	@Test
	public void shouldHonourEqualsHashcodeContract() {
		EqualsVerifier.forClass(AnswerOption.class)
				.verify();
	}
}