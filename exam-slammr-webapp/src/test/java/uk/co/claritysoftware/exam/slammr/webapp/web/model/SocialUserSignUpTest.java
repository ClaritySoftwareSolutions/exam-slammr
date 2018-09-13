package uk.co.claritysoftware.exam.slammr.webapp.web.model;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * Unit test class for {@link SocialUserSignUp}
 */
public class SocialUserSignUpTest {

	@Test
	public void shouldHonourEqualsHashcodeContract() {
		EqualsVerifier.forClass(SocialUserSignUp.class)
				.suppress(Warning.NONFINAL_FIELDS)
				.verify();
	}

}