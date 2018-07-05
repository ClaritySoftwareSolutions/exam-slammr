package uk.co.claritysoftware.exam.slammr.user.service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.co.claritysoftware.exam.slammr.user.service.dynamodb.UserProfileItem;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static uk.co.claritysoftware.exam.slammr.user.testsupport.service.dynamodb.UserProfileItemTestDataFactory.mrBurnsUserProfileItem;

/**
 * Unit test class for {@link UserProfileService}
 */
@RunWith(MockitoJUnitRunner.class)
public class UserProfileServiceTest {

    @Mock
    private DynamoDBMapper dynamoDBMapper;

    @InjectMocks
    private UserProfileService service;

    @Test
    public void shouldGetUserProfile() {
        // Given
        String identityId = "12345";
        UserProfileItem expectedUserProfileItem = mrBurnsUserProfileItem().build();
        given(dynamoDBMapper.load(UserProfileItem.class, identityId))
                .willReturn(expectedUserProfileItem);

        // When
        Optional<UserProfileItem> userProfile = service.getUserProfile(identityId);

        // Then
        assertThat(userProfile)
                .as("Optional UserProfileItem should be present")
                .isPresent()
                .get()
                .as("UserProfileItem is the expected UserProfile")
                .isEqualTo(expectedUserProfileItem);
    }

    @Test
    public void shouldNotGetUserProfileGivenNonExistentId() {
        // Given
        String identityId = "12345";
        given(dynamoDBMapper.load(UserProfileItem.class, identityId))
                .willReturn(null);

        // When
        Optional<UserProfileItem> userProfile = service.getUserProfile(identityId);

        // Then
        assertThat(userProfile)
                .as("Optional UserProfileItem should not be present")
                .isNotPresent();
    }

    @Test
    public void shouldDetermineIfUserProfileExistsGivenUserProfileExists() {
        // Given
        String identityId = "12345";
        UserProfileItem expectedUserProfileItem = mrBurnsUserProfileItem().build();
        given(dynamoDBMapper.load(UserProfileItem.class, identityId))
                .willReturn(expectedUserProfileItem);

        // When
        boolean userProfileExists = service.userProfileExists(identityId);

        // Then
        assertThat(userProfileExists)
                .as("UserProfile exists")
                .isTrue();
    }

    @Test
    public void shouldDetermineIfUserProfileExistsGivenUserProfileDoesNotExist() {
        // Given
        String identityId = "12345";
        given(dynamoDBMapper.load(UserProfileItem.class, identityId))
                .willReturn(null);

        // When
        boolean userProfileExists = service.userProfileExists(identityId);

        // Then
        assertThat(userProfileExists)
                .as("UserProfile does not exist")
                .isFalse();
    }

}