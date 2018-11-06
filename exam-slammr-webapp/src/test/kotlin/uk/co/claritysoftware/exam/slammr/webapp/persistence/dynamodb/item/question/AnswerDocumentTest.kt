package uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.item.question

import nl.jqno.equalsverifier.EqualsVerifier
import org.junit.Test

/**
 * Unit test class for [AnswerDocument]
 */
class AnswerDocumentTest {

	@Test
	fun shouldHonourEqualsHashcodeContract() {
		EqualsVerifier.forClass(AnswerDocument::class.java)
				.verify()
	}

}