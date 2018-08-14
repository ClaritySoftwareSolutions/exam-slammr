package uk.co.claritysoftware.exam.slammr.rest.user.service;

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
import uk.co.claritysoftware.exam.slammr.rest.user.service.dynamodb.UserProfileItem;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doThrow;
import static uk.co.claritysoftware.exam.slammr.rest.user.testsupport.service.dynamodb.UserProfileItemTestDataFactory.mrBurnsUserProfileItem;
import static uk.co.claritysoftware.exam.slammr.rest.user.testsupport.service.dynamodb.UserProfileItemTestDataFactory.smithersUserProfileItem;

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
        given(dynamoDBMapper.load(any(Class.class), any(String.class)))
                .willReturn(expectedUserProfileItem);

        // When
        Optional<UserProfileItem> userProfile = service.getUserProfile(identityId);

        // Then
        then(dynamoDBMapper).should().load(UserProfileItem.class, identityId);
        assertThat(userProfile)
                .as("Optional UserProfileItem should be present")
                .isPresent()
                .get()
                .as("Returned UserProfileItem is the expected UserProfileItem")
                .isEqualTo(expectedUserProfileItem);
    }

    @Test
    public void shouldNotGetUserProfileGivenNonExistentId() {
        // Given
        String identityId = "1234";
        given(dynamoDBMapper.load(any(Class.class), any(String.class)))
                .willReturn(null);

        // When
        Optional<UserProfileItem> userProfile = service.getUserProfile(identityId);

        // Then
        then(dynamoDBMapper).should().load(UserProfileItem.class, identityId);
        assertThat(userProfile)
                .as("Optional UserProfileItem should not be present")
                .isNotPresent();
    }

    @Test
    public void shouldRegisterUserProfile() {
        // Given
        String identityId = "67890";
        UserProfileItem newUserProfileItem = smithersUserProfileItem()
                .webFederatedUserId(identityId)
                .build();

        // When
        Optional<String> createdUserIdentityId = service.registerUserProfile(newUserProfileItem);

        // Then
        then(dynamoDBMapper).should().save(eq(newUserProfileItem), ArgumentMatchers.<DynamoDBSaveExpression>argThat(saveExpression ->
                saveExpression.getExpected().equals(ImmutableMap.of("webFederatedUserId", new ExpectedAttributeValue(false)))
        ));
        assertThat(createdUserIdentityId)
                .as("Optional String should be present")
                .isPresent()
                .get()
                .as("Returned String is the expected identity id")
                .isEqualTo(identityId);
    }

    @Test
    public void shouldNotRegisterUserProfileGivenProfileAlreadyExists() {
        // Given
        String identityId = "67890";
        UserProfileItem newUserProfileItem = smithersUserProfileItem()
                .webFederatedUserId(identityId)
                .build();
        doThrow(new ConditionalCheckFailedException("Conditional Check Failed - User exists"))
                .when(dynamoDBMapper).save(any(UserProfileItem.class), any(DynamoDBSaveExpression.class));

        // When
        Optional<String> createdUserIdentityId = service.registerUserProfile(newUserProfileItem);

        // Then
        then(dynamoDBMapper).should().save(eq(newUserProfileItem), ArgumentMatchers.<DynamoDBSaveExpression>argThat(saveExpression ->
                saveExpression.getExpected().equals(ImmutableMap.of("webFederatedUserId", new ExpectedAttributeValue(false)))
        ));
        assertThat(createdUserIdentityId)
                .as("Optional String should not be present")
                .isNotPresent();
    }

}