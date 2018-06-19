package uk.co.claritysoftware.exam.slammr.user.service.domain;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

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