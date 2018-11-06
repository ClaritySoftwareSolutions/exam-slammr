package uk.co.claritysoftware.exam.slammr.webapp.testsupport.persistence.dynamodb.item.user

import uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.item.user.UserProfileItem
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter.ISO_ZONED_DATE_TIME
import java.util.Arrays.asList

/**
 * Test data factory for [UserProfileItem] instances
 */
class UserProfileItemTestDataFactory {

	companion object {

		/**
		 * @return a User Profile Item Builder containing a valid user profile item, ready to have it's build method called
		 */
		@JvmStatic
		fun mrBurnsUserProfileItem(): UserProfileItem.UserProfileItemBuilder {
			return UserProfileItem.builder()
					.id("12345")
					.compositeUserId("twitter:09876")
					.firstname("Montgomery")
					.surname("Burns")
					.nickname("Mr Burns")
					.email("monty.burns@springfield.com")
					.profilePictureUrl("http://profile.pics/monty.burns")
					.roles(asList("USER"))
					.registrationDateTime(ZonedDateTime.parse("2018-01-20T12:20:30.123Z", ISO_ZONED_DATE_TIME))
					.lastLogonDateTime(ZonedDateTime.parse("2018-05-09T08:12:43.456Z", ISO_ZONED_DATE_TIME))
		}

		/**
		 * @return a User Profile Item Builder containing a valid user profile item, ready to have it's build method called
		 */
		@JvmStatic
		fun smithersUserProfileItem(): UserProfileItem.UserProfileItemBuilder {
			return UserProfileItem.builder()
					.id("67890")
					.compositeUserId("facebook:54321")
					.firstname("Waylon")
					.surname("Smithers")
					.nickname("Smithers")
					.email("waylon.smithers@springfield.com")
					.profilePictureUrl("http://profile.pics/waylon.smithers")
					.roles(asList("USER"))
					.registrationDateTime(ZonedDateTime.parse("2018-01-20T12:20:30.123Z", ISO_ZONED_DATE_TIME))
					.lastLogonDateTime(ZonedDateTime.parse("2018-05-09T08:12:43.456Z", ISO_ZONED_DATE_TIME))
		}
	}
}