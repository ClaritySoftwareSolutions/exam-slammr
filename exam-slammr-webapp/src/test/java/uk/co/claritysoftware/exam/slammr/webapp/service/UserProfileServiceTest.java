package uk.co.claritysoftware.exam.slammr.webapp.service;

import static com.google.common.collect.Sets.newHashSet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import java.util.Optional;
import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.item.user.UserProfileItem;
import uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.repository.DynamoDbUserProfileItemRepository;
import uk.co.claritysoftware.exam.slammr.webapp.service.model.ExamSlammrUserProfile;

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
		UserProfileItem userProfileItem = UserProfileItem.builder()
				.id(id)
				.compositeUserId(userId)
				.roles(newHashSet("USER"))
				.build();

		given(userProfileItemRepository.findByCompositeUserId(any()))
				.willReturn(userProfileItem);

		ExamSlammrUserProfile expectedUserProfile = ExamSlammrUserProfile.builder()
				.id(id)
				.compositeUserId(userId)
				.roles(newHashSet("USER"))
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
}