package uk.co.claritysoftware.exam.slammr.webapp.service.model.question

import nl.jqno.equalsverifier.EqualsVerifier
import org.junit.Test

/**
 * Unit test class for [Question]
 */
class QuestionTest {

	@Test
	fun shouldHonourEqualsHashcodeContract() {
		EqualsVerifier.forClass(Question::class.java)
				.verify()
	}
}