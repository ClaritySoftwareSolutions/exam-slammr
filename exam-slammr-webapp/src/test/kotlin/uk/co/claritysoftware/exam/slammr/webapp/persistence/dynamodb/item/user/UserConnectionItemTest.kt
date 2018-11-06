package uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.item.user

import nl.jqno.equalsverifier.EqualsVerifier
import org.junit.Test

/**
 * Unit test class for [UserConnectionItem]
 */
class UserConnectionItemTest {

	@Test
	fun shouldHonourEqualsHashcodeContract() {
		EqualsVerifier.forClass(UserConnectionItem::class.java)
				.verify()
	}
}