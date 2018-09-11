package uk.co.claritysoftware.exam.slammr.webapp.service.dynamodb.item;

import org.junit.Test;

import uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.item.UserConnectionItem;

import nl.jqno.equalsverifier.EqualsVerifier;

/**
 * Unit test class for {@link UserConnectionItem}
 */
public class UserConnectionItemTest {

	@Test
	public void shouldHonourEqualsHashcodeContract() {
		EqualsVerifier.forClass(UserConnectionItem.class)
				.verify();
	}
}