package uk.co.claritysoftware.exam.slammr.rest.user.factory;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.ZonedDateTime;
import org.junit.Test;
import uk.co.claritysoftware.exam.slammr.rest.user.web.model.UserProfile;
import uk.co.claritysoftware.exam.slammr.rest.user.service.dynamodb.UserProfileItem;

/**
 * Unit test class for {@link UserProfileFactory}
 */
public class UserProfileFactoryTest {

    private UserProfileFactory factory = new UserProfileFactory();

    @Test
    public void shouldReturnValueOfGivenUserProfileItem() {
        // Given
        ZonedDateTime lastLogon = ZonedDateTime.now();
        ZonedDateTime registrationDate = lastLogon.minusDays(1);

        UserProfileItem userProfileItem = UserProfileItem.builder()
                .webFederatedUserId("id")
                .firstname("firstname")
                .surname("surname")
                .nickname("nickname")
                .email("email")
                .profilePictureUrl("profile pic")
                .registrationDateTime(registrationDate)
                .lastLogonDateTime(lastLogon)
                .build();

        UserProfile expectedUserProfile = UserProfile.builder()
                .firstname("firstname")
                .surname("surname")
                .nickname("nickname")
                .email("email")
                .profilePictureUrl("profile pic")
                .registrationDateTime(registrationDate)
                .lastLogonDateTime(lastLogon)
                .build();

        // When
        UserProfile userProfile = factory.valueOf(userProfileItem);

        // Then
        assertThat(userProfile)
                .as("Created UserProfile is as expected")
                .isEqualToComparingFieldByField(expectedUserProfile);
    }
}