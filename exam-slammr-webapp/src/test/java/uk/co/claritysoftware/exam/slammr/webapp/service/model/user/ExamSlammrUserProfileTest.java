package uk.co.claritysoftware.exam.slammr.webapp.service.model.user;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import uk.co.claritysoftware.exam.slammr.webapp.service.model.user.ExamSlammrUserProfile;

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