package uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.item.user

import nl.jqno.equalsverifier.EqualsVerifier
import org.junit.Test

/**
 * Unit test class for [UserProfileItem]
 */
class UserProfileItemTest {

	@Test
	fun shouldHonourEqualsHashcodeContract() {
		EqualsVerifier.forClass(UserProfileItem::class.java)
				.verify()
	}
}