package uk.co.claritysoftware.exam.slammr.webapp.service.model.question

import nl.jqno.equalsverifier.EqualsVerifier
import org.junit.Test

/**
 * Unit test class for [AnswerOption]
 */
class AnswerOptionTest {

	@Test
	fun shouldHonourEqualsHashcodeContract() {
		EqualsVerifier.forClass(AnswerOption::class.java)
				.verify()
	}
}