package uk.co.claritysoftware.exam.slammr.rest.user.delegate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.co.claritysoftware.exam.slammr.rest.user.exception.UserProfileAlreadyRegisteredException;
import uk.co.claritysoftware.exam.slammr.rest.user.exception.UserProfileNotFoundException;
import uk.co.claritysoftware.exam.slammr.rest.user.factory.UserProfileFactory;
import uk.co.claritysoftware.exam.slammr.rest.user.factory.UserProfileItemFactory;
import uk.co.claritysoftware.exam.slammr.rest.user.service.UserProfileService;
import uk.co.claritysoftware.exam.slammr.rest.user.service.dynamodb.UserProfileItem;
import uk.co.claritysoftware.exam.slammr.rest.user.web.model.UserProfile;
import uk.co.claritysoftware.exam.slammr.rest.user.web.model.UserRegistrationRequest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static uk.co.claritysoftware.exam.slammr.rest.user.testsupport.service.dynamodb.UserProfileItemTestDataFactory.mrBurnsUserProfileItem;
import static uk.co.claritysoftware.exam.slammr.rest.user.testsupport.service.dynamodb.UserProfileItemTestDataFactory.smithersUserProfileItem;
import static uk.co.claritysoftware.exam.slammr.rest.user.testsupport.web.model.UserProfileTestDataFactory.mrBurnsUserProfile;
import static uk.co.claritysoftware.exam.slammr.rest.user.testsupport.web.model.UserRegistrationRequestTestDataFactory.smithersUserRegistrationRequest;

/**
 * Unit test class for {@link UserDelegate}
 */
@RunWith(MockitoJUnitRunner.class)
public class UserDelegateTest {

    @Mock
    private UserProfileService userProfileService;

    @Mock
    private UserProfileFactory userProfileFactory;

    @Mock
    private UserProfileItemFactory userProfileItemFactory;

    @InjectMocks
    private UserDelegate userDelegate;

    @Test
    public void shouldGetUserProfile() {
        // Given
        String identityId = "12345";
        UserProfileItem expectedUserProfileItem = mrBurnsUserProfileItem().build();
        given(userProfileService.getUserProfile(any(String.class)))
                .willReturn(Optional.of(expectedUserProfileItem));

        UserProfile expectedUserProfile = mrBurnsUserProfile().build();
        given(userProfileFactory.valueOf(any(UserProfileItem.class)))
                .willReturn(expectedUserProfile);

        // When
        UserProfile userProfile = userDelegate.getUserProfile(identityId);

        // Then
        then(userProfileService).should().getUserProfile(identityId);
        then(userProfileFactory).should().valueOf(expectedUserProfileItem);
        assertThat(userProfile)
                .as("Correct UserProfile returned")
                .isEqualTo(expectedUserProfile);
    }

    @Test
    public void shouldFailToGetUserProfileGivenServiceReturnsEmpty() {
        // Given
        String identityId = "12345";
        given(userProfileService.getUserProfile(any(String.class)))
                .willReturn(Optional.empty());

        // When
        Throwable throwable = catchThrowable(() -> userDelegate.getUserProfile(identityId));

        // Then
        assertThat(throwable)
                .as("Expected exception thrown")
                .isInstanceOf(UserProfileNotFoundException.class)
                .hasMessage("UserProfile with id 12345 not found");
    }

    @Test
    public void shouldRegisterUserProfile() {
        // Given
        String identityId = "67890";
        UserRegistrationRequest userRegistrationRequest = smithersUserRegistrationRequest().build();

        UserProfileItem expectedNewUserProfileItem = smithersUserProfileItem().build();
        given(userProfileItemFactory.valueOf(any(), any()))
                .willReturn(expectedNewUserProfileItem);

        given(userProfileService.registerUserProfile(any(UserProfileItem.class)))
                .willReturn(Optional.of(identityId));

        // When
        userDelegate.registerUserProfile(userRegistrationRequest, identityId);

        // Then
        then(userProfileItemFactory).should().valueOf(userRegistrationRequest, identityId);
        then(userProfileService).should().registerUserProfile(expectedNewUserProfileItem);
    }

    @Test
    public void shouldFailToRegisterUserProfileGivenServiceReturnsEmpty() {
        // Given
        String identityId = "67890";
        UserRegistrationRequest userRegistrationRequest = smithersUserRegistrationRequest().build();

        UserProfileItem expectedNewUserProfileItem = smithersUserProfileItem().build();
        given(userProfileItemFactory.valueOf(any(), any()))
                .willReturn(expectedNewUserProfileItem);

        given(userProfileService.registerUserProfile(any(UserProfileItem.class)))
                .willReturn(Optional.empty());

        // When
        Throwable throwable = catchThrowable(() -> userDelegate.registerUserProfile(userRegistrationRequest, identityId));

        // Then
        then(userProfileItemFactory).should().valueOf(userRegistrationRequest, identityId);
        then(userProfileService).should().registerUserProfile(expectedNewUserProfileItem);
        assertThat(throwable)
                .as("UserProfileAlreadyRegisteredException was thrown")
                .isInstanceOf(UserProfileAlreadyRegisteredException.class)
                .hasMessage("UserProfile id 67890 already registered");
    }
}