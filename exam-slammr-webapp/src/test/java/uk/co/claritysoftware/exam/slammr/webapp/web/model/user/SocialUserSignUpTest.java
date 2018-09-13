package uk.co.claritysoftware.exam.slammr.webapp.web.model.user;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import uk.co.claritysoftware.exam.slammr.webapp.web.model.user.SocialUserSignUp;

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