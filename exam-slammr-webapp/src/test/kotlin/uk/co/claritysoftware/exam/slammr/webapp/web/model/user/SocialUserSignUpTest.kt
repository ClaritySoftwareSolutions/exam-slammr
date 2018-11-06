package uk.co.claritysoftware.exam.slammr.webapp.web.model.user

import nl.jqno.equalsverifier.EqualsVerifier
import nl.jqno.equalsverifier.Warning
import org.junit.Test

/**
 * Unit test class for [SocialUserSignUp]
 */
class SocialUserSignUpTest {

	@Test
	fun shouldHonourEqualsHashcodeContract() {
		EqualsVerifier.forClass(SocialUserSignUp::class.java)
				.suppress(Warning.NONFINAL_FIELDS)
				.verify()
	}

}