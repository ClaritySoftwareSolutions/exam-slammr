package uk.co.claritysoftware.exam.slammr.webapp.service.factory;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.claritysoftware.exam.slammr.webapp.testsupport.persistence.dynamodb.item.user.UserProfileItemTestDataFactory.mrBurnsUserProfileItem;
import static uk.co.claritysoftware.exam.slammr.webapp.testsupport.service.model.user.ExamSlammrUserProfileTestDataFactory.mrBurnsExamSlammrUserProfile;

import org.junit.Test;
import uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.item.user.UserProfileItem;
import uk.co.claritysoftware.exam.slammr.webapp.service.model.user.ExamSlammrUserProfile;

/**
 * Unit test class for {@link ExamSlammrUserProfileFactory}
 */
public class ExamSlammrUserProfileFactoryTest {

    @Test
    public void shouldDeriveExamSlammrUserProfileValueOfGivenUserProfileItem() {
        // Given
        UserProfileItem userProfileItem = mrBurnsUserProfileItem().build();
        ExamSlammrUserProfile expectedExamSlammrUserProfile = mrBurnsExamSlammrUserProfile().build();

        // When
        ExamSlammrUserProfile examSlammrUserProfile = ExamSlammrUserProfileFactory.valueOf(userProfileItem);

        // Then
        assertThat(examSlammrUserProfile)
                .isEqualTo(expectedExamSlammrUserProfile);
    }

    @Test
    public void shouldNotDeriveExamSlammrUserProfileValueOfGivenNullUserProfileItem() {
        // Given
        UserProfileItem userProfileItem = null;

        // When
        ExamSlammrUserProfile examSlammrUserProfile = ExamSlammrUserProfileFactory.valueOf(userProfileItem);

        // Then
        assertThat(examSlammrUserProfile)
                .isNull();
    }

    @Test
    public void shouldDeriveUserProfileItemValueOfGivenExamSlammrUserProfile() {
        // Given
        ExamSlammrUserProfile examSlammrUserProfile = mrBurnsExamSlammrUserProfile().build();
        UserProfileItem expectedUserProfileItem = mrBurnsUserProfileItem().build();

        // When
        UserProfileItem userProfileItem = ExamSlammrUserProfileFactory.valueOf(examSlammrUserProfile);

        // Then
        assertThat(userProfileItem)
                .isEqualTo(expectedUserProfileItem);
    }

    @Test
    public void shouldNotDeriveUserProfileItemValueOfGivenNullExamSlammrUserProfile() {
        // Given
        ExamSlammrUserProfile examSlammrUserProfile = null;

        // When
        UserProfileItem userProfileItem = ExamSlammrUserProfileFactory.valueOf(examSlammrUserProfile);

        // Then
        assertThat(userProfileItem)
                .isNull();
    }
}