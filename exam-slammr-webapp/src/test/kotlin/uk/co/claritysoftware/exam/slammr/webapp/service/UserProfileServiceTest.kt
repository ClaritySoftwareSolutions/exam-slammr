package uk.co.claritysoftware.exam.slammr.webapp.service

import com.nhaarman.mockitokotlin2.any
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.item.user.UserProfileItem
import uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.repository.DynamoDbUserProfileItemRepository
import uk.co.claritysoftware.exam.slammr.webapp.testsupport.persistence.dynamodb.item.user.UserProfileItemTestDataFactory.Companion.smithersUserProfileItem
import uk.co.claritysoftware.exam.slammr.webapp.testsupport.service.model.user.ExamSlammrUserProfileTestDataFactory.Companion.smithersExamSlammrUserProfile
import java.util.*

/**
 * Unit test class for {@link UserProfileService}
 */
@RunWith(MockitoJUnitRunner::class)
class UserProfileServiceTest {

	@Mock
	private lateinit var userProfileItemRepository: DynamoDbUserProfileItemRepository

	@InjectMocks
	private lateinit var userProfileService: UserProfileService

	@Test
	fun shouldGetUserProfileByIdGivenIdOfUserProfileRecord() {
		// Given
		val userId = "twitter:12345"
		val id = UUID.randomUUID().toString()
		val userProfileItem = smithersUserProfileItem().copy(
				id = id,
				compositeUserId = userId)

		given(userProfileItemRepository.findByCompositeUserId(any()))
				.willReturn(userProfileItem)

		val expectedUserProfile = smithersExamSlammrUserProfile().copy(
				id = id,
				compositeUserId = userId)

		// When
		val userProfile = userProfileService.getUserProfileByUserId(userId)

		// Then
		then(userProfileItemRepository).should().findByCompositeUserId(userId)
		assertThat(userProfile)
				.isPresent()
				.get()
				.isEqualTo(expectedUserProfile)

	}

	@Test
	fun shouldNotGetUserProfileByIdGivenIdOfNonExistentUserProfileRecord() {
		// Given
		val userId = "twitter:67890"

		given(userProfileItemRepository.findByCompositeUserId(any()))
				.willReturn(null)

		// When
		val userProfile = userProfileService.getUserProfileByUserId(userId)

		// Then
		then(userProfileItemRepository).should().findByCompositeUserId(userId)
		assertThat(userProfile)
				.isNotPresent()
	}

	@Test
	fun shouldSaveUserProfile() {
		// Given
		val id = UUID.randomUUID().toString()

		val newUserProfile = smithersExamSlammrUserProfile()
				.copy(id = null)

		val newUserProfileItem = smithersUserProfileItem()
				.copy(id = null)

		val savedUserProfile = smithersExamSlammrUserProfile()
				.copy(id = id)

		val savedUserProfileItem = smithersUserProfileItem()
				.copy(id = id)

		given(userProfileItemRepository.save(any<UserProfileItem>()))
				.willReturn(savedUserProfileItem)

		// When
		val examSlammrUserProfile = userProfileService.saveUserProfile(newUserProfile)

		// Then
		then(userProfileItemRepository).should().save(newUserProfileItem)
		assertThat(examSlammrUserProfile)
				.isEqualTo(savedUserProfile)
	}

}