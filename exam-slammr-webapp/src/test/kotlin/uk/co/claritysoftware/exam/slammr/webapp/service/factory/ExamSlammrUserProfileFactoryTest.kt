package uk.co.claritysoftware.exam.slammr.webapp.service.factory

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import uk.co.claritysoftware.exam.slammr.webapp.factory.valueOf
import uk.co.claritysoftware.exam.slammr.webapp.testsupport.persistence.dynamodb.item.user.UserProfileItemTestDataFactory.Companion.mrBurnsUserProfileItem
import uk.co.claritysoftware.exam.slammr.webapp.testsupport.service.model.user.ExamSlammrUserProfileTestDataFactory.Companion.mrBurnsExamSlammrUserProfile

/**
 * Unit test class for [ExamSlammrUserProfileFactory]
 */
class ExamSlammrUserProfileFactoryTest {

	@Test
	fun shouldDeriveExamSlammrUserProfileValueOfGivenUserProfileItem() {
		// Given
		val userProfileItem = mrBurnsUserProfileItem()
		val expectedExamSlammrUserProfile = mrBurnsExamSlammrUserProfile()

		// When
		val examSlammrUserProfile = valueOf(userProfileItem)

		// Then
		assertThat(examSlammrUserProfile)
				.isEqualTo(expectedExamSlammrUserProfile)
	}

	@Test
	fun shouldDeriveUserProfileItemValueOfGivenExamSlammrUserProfile() {
		// Given
		val examSlammrUserProfile = mrBurnsExamSlammrUserProfile()
		val expectedUserProfileItem = mrBurnsUserProfileItem()

		// When
		val userProfileItem = valueOf(examSlammrUserProfile)

		// Then
		assertThat(userProfileItem)
				.isEqualTo(expectedUserProfileItem)
	}

}