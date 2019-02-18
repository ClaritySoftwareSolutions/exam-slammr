package uk.co.claritysoftware.exam.slammr.webapp.service

import org.slf4j.LoggerFactory
import uk.co.claritysoftware.exam.slammr.webapp.factory.valueOf
import uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.item.user.UserProfileItem
import uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.repository.DynamoDbUserProfileItemRepository
import uk.co.claritysoftware.exam.slammr.webapp.service.model.user.ExamSlammrUserProfile
import java.util.*

/**
 * Service for User Profile related operations
 */
open class UserProfileService(val userProfileItemRepository: DynamoDbUserProfileItemRepository) {

	private companion object {
		val log = LoggerFactory.getLogger(UserProfileService::class.java)
	}

	/**
	 * Retrieve the User Profile identified by the specified user id. The User Profile is returned within an Optional
	 *
	 * @param userId the id of the User Profile to return
	 * @return an Optional containing the User Profile, or an empty Optional if the User Profile could not be found by
	 * the specified id
	 */
	open fun getUserProfileByUserId(userId: String): Optional<ExamSlammrUserProfile> {

		return userProfileItemRepository.findByCompositeUserId(userId)?.let {
			val retrievedUserProfile = valueOf(it)
			log.debug("Returning ExamSlammrUserProfile {}", retrievedUserProfile)
			Optional.ofNullable(retrievedUserProfile)

		}
				?: Optional.empty()
	}

	/**
	 * Saves a User Profile, returning the newly saved User Profile as the persistence implementation may have updated
	 * some of it's fields such as `id` in the case of a new item
	 *
	 * @param userProfile the [ExamSlammrUserProfile] to be saved
	 * @return the saved [ExamSlammrUserProfile]
	 */
	open fun saveUserProfile(userProfile: ExamSlammrUserProfile): ExamSlammrUserProfile {
		val userProfileItem = valueOf(userProfile)
		val savedUserProfile = valueOf(userProfileItemRepository.save<UserProfileItem>(userProfileItem))
		log.debug("Saved ExamSlammrUserProfile {}", savedUserProfile)
		return savedUserProfile
	}

}