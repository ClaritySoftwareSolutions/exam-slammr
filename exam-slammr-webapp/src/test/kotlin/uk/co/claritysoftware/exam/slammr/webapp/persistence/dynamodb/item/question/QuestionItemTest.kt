package uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.item.question

import nl.jqno.equalsverifier.EqualsVerifier
import org.junit.Test

/**
 * Unit test class for [QuestionItem]
 */
class QuestionItemTest {

	@Test
	fun shouldHonourEqualsHashcodeContract() {
		EqualsVerifier.forClass(QuestionItem::class.java)
				.verify()
	}

}