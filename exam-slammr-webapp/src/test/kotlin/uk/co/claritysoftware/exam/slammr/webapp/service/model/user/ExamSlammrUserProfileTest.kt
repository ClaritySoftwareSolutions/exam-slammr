package uk.co.claritysoftware.exam.slammr.webapp.service.model.user

import nl.jqno.equalsverifier.EqualsVerifier
import org.junit.Test

/**
 * Uuit test class for [ExamSlammrUserProfile]
 */
class ExamSlammrUserProfileTest {

	@Test
	fun shouldHonourEqualsHashcodeContract() {
		EqualsVerifier.forClass(ExamSlammrUserProfile::class.java)
				.verify()
	}
}