package uk.co.claritysoftware.exam.slammr.webapp.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static uk.co.claritysoftware.exam.slammr.webapp.testsupport.persistence.dynamodb.item.user.UserProfileItemTestDataFactory.smithersUserProfileItem;
import static uk.co.claritysoftware.exam.slammr.webapp.testsupport.service.model.user.ExamSlammrUserProfileTestDataFactory.smithersExamSlammrUserProfile;

import java.util.Optional;
import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.item.user.UserProfileItem;
import uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.repository.DynamoDbUserProfileItemRepository;
import uk.co.claritysoftware.exam.slammr.webapp.service.model.user.ExamSlammrUserProfile;

/**
 * Unit test class for {@link UserProfileService}
 */
@RunWith(MockitoJUnitRunner.class)
public class UserProfileServiceTest {

    @Mock
    private DynamoDbUserProfileItemRepository userProfileItemRepository;

    @InjectMocks
    private UserProfileService userProfileService;

    @Test
    public void shouldGetUserProfileByIdGivenIdOfUserProfileRecord() {
        // Given
        String userId = "twitter:12345";
        String id = UUID.randomUUID().toString();
        UserProfileItem userProfileItem = smithersUserProfileItem()
                .id(id)
                .compositeUserId(userId)
                .build();

        given(userProfileItemRepository.findByCompositeUserId(any()))
                .willReturn(userProfileItem);

        ExamSlammrUserProfile expectedUserProfile = smithersExamSlammrUserProfile()
                .id(id)
                .compositeUserId(userId)
                .build();

        // When
        Optional<ExamSlammrUserProfile> userProfile = userProfileService.getUserProfileByUserId(userId);

        // Then
        then(userProfileItemRepository).should().findByCompositeUserId(userId);
        assertThat(userProfile)
                .isPresent()
                .get()
                .isEqualTo(expectedUserProfile);

    }

    @Test
    public void shouldNotGetUserProfileByIdGivenIdOfNonExistentUserProfileRecord() {
        // Given
        String userId = "twitter:67890";

        given(userProfileItemRepository.findByCompositeUserId(any()))
                .willReturn(null);

        // When
        Optional<ExamSlammrUserProfile> userProfile = userProfileService.getUserProfileByUserId(userId);

        // Then
        then(userProfileItemRepository).should().findByCompositeUserId(userId);
        assertThat(userProfile)
                .isNotPresent();
    }

    @Test
    public void shouldSaveUserProfile() {
        // Given
        String id = UUID.randomUUID().toString();

        ExamSlammrUserProfile newUserProfile = smithersExamSlammrUserProfile()
                .id(null)
                .build();

        UserProfileItem newUserProfileItem = smithersUserProfileItem()
                .id(null)
                .build();

        ExamSlammrUserProfile savedUserProfile = smithersExamSlammrUserProfile()
                .id(id)
                .build();

        UserProfileItem savedUserProfileItem = smithersUserProfileItem()
                .id(id)
                .build();

        given(userProfileItemRepository.save(any(UserProfileItem.class)))
                .willReturn(savedUserProfileItem);

        // When
        ExamSlammrUserProfile examSlammrUserProfile = userProfileService.saveUserProfile(newUserProfile);

        // Then
        then(userProfileItemRepository).should().save(newUserProfileItem);
        assertThat(examSlammrUserProfile)
                .isEqualTo(savedUserProfile);
    }

    @Test
    public void shouldFailToSaveUserProfileGivenNullUserProfile() {
        // Given
        ExamSlammrUserProfile newUserProfile = null;

        // When
        Throwable throwable = catchThrowable(() -> userProfileService.saveUserProfile(newUserProfile));

        // Then
        then(userProfileItemRepository).shouldHaveZeroInteractions();
        assertThat(throwable)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Cannot save a null UserProfile");
    }
}