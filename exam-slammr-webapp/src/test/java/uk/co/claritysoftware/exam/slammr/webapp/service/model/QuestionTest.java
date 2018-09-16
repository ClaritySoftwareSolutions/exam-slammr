package uk.co.claritysoftware.exam.slammr.webapp.service.model;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

/**
 * Unit test class for {@link Question}
 */
public class QuestionTest {

	@Test
	public void shouldHonourEqualsHashcodeContract() {
		EqualsVerifier.forClass(Question.class)
				.verify();
	}
}