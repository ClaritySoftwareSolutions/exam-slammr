package uk.co.claritysoftware.examslammr.user.service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.google.common.collect.ImmutableMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.co.claritysoftware.examslammr.user.rest.exception.UserProfileAlreadyRegisteredException;
import uk.co.claritysoftware.examslammr.user.rest.factory.UserProfileItemFactory;
import uk.co.claritysoftware.examslammr.user.rest.model.UserRegistrationRequest;
import uk.co.claritysoftware.examslammr.user.service.dynamodb.UserProfileItem;
import uk.co.claritysoftware.examslammr.user.testsupport.rest.model.UserRegistrationRequestTestDataFactory;
import uk.co.claritysoftware.examslammr.user.testsupport.service.dynamodb.UserProfileItemTestDataFactory;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doThrow;
import static uk.co.claritysoftware.examslammr.user.testsupport.rest.model.UserRegistrationRequestTestDataFactory.smithersUserRegistrationRequest;
import static uk.co.claritysoftware.examslammr.user.testsupport.service.dynamodb.UserProfileItemTestDataFactory.mrBurnsUserProfileItem;
import static uk.co.claritysoftware.examslammr.user.testsupport.service.dynamodb.UserProfileItemTestDataFactory.smithersUserProfileItem;

/**
 * Unit test class for {@link UserProfileService}
 */
@RunWith(MockitoJUnitRunner.class)
public class UserProfileServiceTest {

    @Mock
    private DynamoDBMapper dynamoDBMapper;

    @Mock
    private UserProfileItemFactory userProfileItemFactory;

    @InjectMocks
    private UserProfileService service;

    @Test
    public void shouldGetUserProfile() {
        // Given
        String identityId = "12345";
        UserProfileItem expectedUserProfileItem = UserProfileItemTestDataFactory.mrBurnsUserProfileItem().build();
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
    public void shouldRegisterNewUserProfile() {
        // Given
        String identityId = "67890";
        UserRegistrationRequest userRegistrationRequest = UserRegistrationRequestTestDataFactory.smithersUserRegistrationRequest().build();

        UserProfileItem expectedNewUserProfileItem = UserProfileItemTestDataFactory.smithersUserProfileItem().build();
        given(userProfileItemFactory.valueOf(any(), any()))
                .willReturn(expectedNewUserProfileItem);

        // When
        service.registerUserProfile(userRegistrationRequest, identityId);

        // Then
        then(userProfileItemFactory).should().valueOf(userRegistrationRequest, identityId);
        then(dynamoDBMapper).should().save(eq(expectedNewUserProfileItem), ArgumentMatchers.<DynamoDBSaveExpression>argThat(saveExpression ->
                saveExpression.getExpected().equals(ImmutableMap.of("webFederatedUserId", new ExpectedAttributeValue(false)))
        ));
    }

    @Test
    public void shouldFailToRegisterNewUserProfileGivenProfileAlreadyExists() {
        // Given
        String identityId = "67890";
        UserRegistrationRequest userRegistrationRequest = UserRegistrationRequestTestDataFactory.smithersUserRegistrationRequest().build();

        UserProfileItem expectedNewUserProfileItem = UserProfileItemTestDataFactory.smithersUserProfileItem().build();
        given(userProfileItemFactory.valueOf(any(), any()))
                .willReturn(expectedNewUserProfileItem);

        doThrow(new ConditionalCheckFailedException("Conditional Check Failed - User exists")).when(dynamoDBMapper)
                .save(any(UserProfileItem.class), any(DynamoDBSaveExpression.class));

        // When
        Throwable throwable = catchThrowable(() -> service.registerUserProfile(userRegistrationRequest, identityId));

        // Then
        then(userProfileItemFactory).should().valueOf(userRegistrationRequest, identityId);
        then(dynamoDBMapper).should().save(eq(expectedNewUserProfileItem), ArgumentMatchers.<DynamoDBSaveExpression>argThat(saveExpression ->
                saveExpression.getExpected().equals(ImmutableMap.of("webFederatedUserId", new ExpectedAttributeValue(false)))
        ));
        assertThat(throwable)
                .as("UserProfileAlreadyRegisteredException was thrown")
                .isInstanceOf(UserProfileAlreadyRegisteredException.class)
                .hasMessage("UserProfile id 67890 already registered");
    }

}