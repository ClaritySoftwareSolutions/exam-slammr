package uk.co.claritysoftware.exam.slammr.webapp.service.dynamodb.item;

import org.junit.Test;

import uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.item.UserProfileItem;

import nl.jqno.equalsverifier.EqualsVerifier;

/**
 * Unit test class for {@link UserProfileItem}
 */
public class UserProfileItemTest {

	@Test
	public void shouldHonourEqualsHashcodeContract() {
		EqualsVerifier.forClass(UserProfileItem.class)
				.verify();
	}
}