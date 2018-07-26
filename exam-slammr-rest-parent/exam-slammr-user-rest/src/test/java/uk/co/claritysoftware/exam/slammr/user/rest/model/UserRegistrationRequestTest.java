package uk.co.claritysoftware.exam.slammr.user.rest.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

/**
 * Unit test class for {@link UserRegistrationRequest}
 */
public class UserRegistrationRequestTest {

    @Test
    public void shouldHonourEqualsHashcodeContract() {
        EqualsVerifier.forClass(UserRegistrationRequest.class)
                .verify();
    }

}