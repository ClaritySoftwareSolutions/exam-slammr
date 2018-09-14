package uk.co.claritysoftware.exam.slammr.webapp.service.model;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

/**
 * Uuit test class for {@link ExamSlammrUserProfile}
 */
public class ExamSlammrUserProfileTest {

	@Test
	public void shouldHonourEqualsHashcodeContract() {
		EqualsVerifier.forClass(ExamSlammrUserProfile.class)
				.verify();
	}
}