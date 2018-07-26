package uk.co.claritysoftware.exam.slammr.user.rest.model;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

/**
 * Unit test class for {@link UserProfile}
 */
public class UserProfileTest {

	@Test
	public void shouldHonourEqualsHashcodeContract() {
		EqualsVerifier.forClass(UserProfile.class)
				.verify();
	}

}