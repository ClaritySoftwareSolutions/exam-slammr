package uk.co.claritysoftware.exam.slammr.user.service.dynamodb;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

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