package uk.co.claritysoftware.exam.slammr.webapp.factory

import uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.item.user.UserProfileItem
import uk.co.claritysoftware.exam.slammr.webapp.service.model.user.ExamSlammrUserProfile

/**
 * Factory class to create instances to and from [ExamSlammrUserProfile] instances
 */

/**
 * Creates a new [ExamSlammrUserProfile] from the specified [UserProfileItem]
 *
 * @param userProfileItem the UserProfileItem to create the ExamSlammrUserProfile from
 * @return a populated ExamSlammrUserProfile instance
 */
fun valueOf(userProfileItem: UserProfileItem): ExamSlammrUserProfile {
	return ExamSlammrUserProfile(
			id = userProfileItem.id,
			compositeUserId = userProfileItem.compositeUserId,
			firstname = userProfileItem.firstname,
			surname = userProfileItem.surname,
			nickname = userProfileItem.nickname,
			email = userProfileItem.email,
			profilePictureUrl = userProfileItem.profilePictureUrl,
			roles = userProfileItem.roles.toSet(),
			lastLogonDateTime = userProfileItem.lastLogonDateTime,
			registrationDateTime = userProfileItem.registrationDateTime)
}

/**
 * Creates a new [UserProfileItem] from the specified [ExamSlammrUserProfile]
 *
 * @param userProfile the ExamSlammrUserProfile to create the UserProfileItem from
 * @return a populated UserProfileItem instance
 */
fun valueOf(userProfile: ExamSlammrUserProfile): UserProfileItem {
	return UserProfileItem(
			id = userProfile.id,
			compositeUserId = userProfile.compositeUserId,
			firstname = userProfile.firstname,
			surname = userProfile.surname,
			nickname = userProfile.nickname,
			email = userProfile.email,
			profilePictureUrl = userProfile.profilePictureUrl,
			roles = userProfile.roles!!.toList(),
			lastLogonDateTime = userProfile.lastLogonDateTime,
			registrationDateTime = userProfile.registrationDateTime)
}
