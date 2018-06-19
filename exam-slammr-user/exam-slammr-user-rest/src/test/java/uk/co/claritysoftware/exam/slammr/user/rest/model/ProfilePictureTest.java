package uk.co.claritysoftware.exam.slammr.user.rest.model;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

/**
 * Unit test class for {@link ProfilePicture}
 */
public class ProfilePictureTest {

	@Test
	public void shouldHonourEqualsHashcodeContract() {
		EqualsVerifier.forClass(ProfilePicture.class)
				.verify();
	}

}