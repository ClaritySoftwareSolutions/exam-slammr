package uk.co.claritysoftware.exam.slammr.webapp.service.model.question

import nl.jqno.equalsverifier.EqualsVerifier
import org.junit.Test

/**
 * Unit test class for [FurtherReading]
 */
class FurtherReadingTest {

	@Test
	fun shouldHonourEqualsHashcodeContract() {
		EqualsVerifier.forClass(FurtherReading::class.java)
				.verify()
	}
}