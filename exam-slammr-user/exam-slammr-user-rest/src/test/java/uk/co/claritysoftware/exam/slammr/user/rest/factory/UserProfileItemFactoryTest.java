package uk.co.claritysoftware.exam.slammr.user.rest.factory;

import org.junit.Test;
import uk.co.claritysoftware.exam.slammr.user.rest.model.UserRegistrationRequest;
import uk.co.claritysoftware.exam.slammr.user.service.dynamodb.UserProfileItem;

import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.claritysoftware.exam.slammr.user.testsupport.rest.model.UserRegistrationRequestTestDataFactory.smithersUserRegistrationRequest;
import static uk.co.claritysoftware.exam.slammr.user.testsupport.service.dynamodb.UserProfileItemTestDataFactory.smithersUserProfileItem;

/**
 * Unit test class for {@link UserProfileItemFactory}
 */
public class UserProfileItemFactoryTest {

    private UserProfileItemFactory factory = new UserProfileItemFactory();

    @Test
    public void shouldReturnValueOfGivenUserRegistrationRequestAndIdentityId() {
        // Given
        ZonedDateTime earliestExpectedDate = ZonedDateTime.now();

        String identityId = "67890";
        UserRegistrationRequest userRegistrationRequest = smithersUserRegistrationRequest().build();

        UserProfileItem expectedUserProfileItem = smithersUserProfileItem()
                .registrationDateTime(earliestExpectedDate)
                .lastLogonDateTime(earliestExpectedDate)
                .build();

        // When
        UserProfileItem userProfileItem = factory.valueOf(userRegistrationRequest, identityId);

        // Then
        assertThat(userProfileItem)
                .as("UserProfileItem has expected fields")
                .isEqualToIgnoringGivenFields(expectedUserProfileItem, "registrationDateTime", "lastLogonDateTime");
        assertThat(userProfileItem.getRegistrationDateTime())
                .as("RegistrationDateTime is not before the earliest expected value")
                .isAfterOrEqualTo(earliestExpectedDate);
        assertThat(userProfileItem.getLastLogonDateTime())
                .as("LastLogonDateTime is not before the earliest expected value")
                .isAfterOrEqualTo(earliestExpectedDate);
    }
}