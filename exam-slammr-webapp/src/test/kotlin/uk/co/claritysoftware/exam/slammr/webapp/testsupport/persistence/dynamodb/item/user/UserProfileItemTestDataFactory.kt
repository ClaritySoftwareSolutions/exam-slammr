package uk.co.claritysoftware.exam.slammr.webapp.testsupport.persistence.dynamodb.item.user

import uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.item.user.UserProfileItem
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter.ISO_ZONED_DATE_TIME

/**
 * Test data factory for [UserProfileItem] instances
 */
class UserProfileItemTestDataFactory {

	companion object {

		/**
		 * @return a User Profile Item containing a valid user profile item
		 */
		@JvmStatic
		fun mrBurnsUserProfileItem() = UserProfileItem(
				id = "12345",
				compositeUserId = "twitter:09876",
				firstname = "Montgomery",
				surname = "Burns",
				nickname = "Mr Burns",
				email = "monty.burns@springfield.com",
				profilePictureUrl = "http://profile.pics/monty.burns",
				roles = listOf("USER"),
				registrationDateTime = ZonedDateTime.parse("2018-01-20T12:20:30.123Z", ISO_ZONED_DATE_TIME),
				lastLogonDateTime = ZonedDateTime.parse("2018-05-09T08:12:43.456Z", ISO_ZONED_DATE_TIME))


		/**
		 * @return a User Profile Item containing a valid user profile item
		 */
		@JvmStatic
		fun smithersUserProfileItem() = UserProfileItem(
				id = "67890",
				compositeUserId = "facebook:54321",
				firstname = "Waylon",
				surname = "Smithers",
				nickname = "Smithers",
				email = "waylon.smithers@springfield.com",
				profilePictureUrl = "http://profile.pics/waylon.smithers",
				roles = listOf("USER"),
				registrationDateTime = ZonedDateTime.parse("2018-01-20T12:20:30.123Z", ISO_ZONED_DATE_TIME),
				lastLogonDateTime = ZonedDateTime.parse("2018-05-09T08:12:43.456Z", ISO_ZONED_DATE_TIME))
	}
}