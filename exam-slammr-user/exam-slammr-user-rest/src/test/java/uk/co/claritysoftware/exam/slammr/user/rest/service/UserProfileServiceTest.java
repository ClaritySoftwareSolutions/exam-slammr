package uk.co.claritysoftware.exam.slammr.user.rest.service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.co.claritysoftware.exam.slammr.user.rest.model.UserProfile;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static uk.co.claritysoftware.exam.slammr.user.rest.testsupport.model.UserProfileTestDataFactory.aValidUserProfile;

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
        UserProfile expectedUserProfile = aValidUserProfile().build();
        given(dynamoDBMapper.load(UserProfile.class, identityId))
                .willReturn(expectedUserProfile);

        // When
        Optional<UserProfile> userProfile = service.getUserProfile(identityId);

        // Then
        assertThat(userProfile)
                .as("Optional UserProfile should be present")
                .isPresent()
                .get()
                .as("UserProfile is the expected UserProfile")
                .isEqualTo(expectedUserProfile);
    }

    @Test
    public void shouldNotGetUserProfileGivenNonExistentId() {
        // Given
        String identityId = "12345";
        given(dynamoDBMapper.load(UserProfile.class, identityId))
                .willReturn(null);

        // When
        Optional<UserProfile> userProfile = service.getUserProfile(identityId);

        // Then
        assertThat(userProfile)
                .as("Optional UserProfile should not be present")
                .isNotPresent();
    }
}