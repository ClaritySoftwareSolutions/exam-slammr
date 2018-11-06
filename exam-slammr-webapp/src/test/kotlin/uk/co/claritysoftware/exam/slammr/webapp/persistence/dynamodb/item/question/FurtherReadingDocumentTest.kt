package uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.item.question

import nl.jqno.equalsverifier.EqualsVerifier
import org.junit.Test

/**
 * Unit test class for [FurtherReadingDocument]
 */
class FurtherReadingDocumentTest {

	@Test
	fun shouldHonourEqualsHashcodeContract() {
		EqualsVerifier.forClass(FurtherReadingDocument::class.java)
				.verify()
	}
}