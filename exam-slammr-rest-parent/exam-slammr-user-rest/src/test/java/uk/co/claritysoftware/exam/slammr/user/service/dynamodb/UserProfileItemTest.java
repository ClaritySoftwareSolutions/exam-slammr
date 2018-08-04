package uk.co.claritysoftware.exam.slammr.user.service.dynamodb;

import org.junit.Test;

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