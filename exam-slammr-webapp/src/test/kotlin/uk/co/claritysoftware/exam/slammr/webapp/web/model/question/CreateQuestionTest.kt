package uk.co.claritysoftware.exam.slammr.webapp.web.model.question

import nl.jqno.equalsverifier.EqualsVerifier
import nl.jqno.equalsverifier.Warning
import org.junit.Test

/**
 * Unit test class for [CreateQuestion]
 */
class CreateQuestionTest {

	@Test
	fun shouldHonourEqualsHashcodeContract() {
		EqualsVerifier.forClass(CreateQuestion::class.java)
				.suppress(Warning.NONFINAL_FIELDS)
				.verify()
	}

}