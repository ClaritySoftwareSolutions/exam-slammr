package uk.co.claritysoftware.exam.slammr.webapp.testsupport.service.model.user

import com.google.common.collect.Sets.newHashSet
import uk.co.claritysoftware.exam.slammr.webapp.service.model.user.ExamSlammrUserProfile
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter.ISO_ZONED_DATE_TIME

/**
 * Test data factory for []ExamSlammrUserProfile] instances
 */
class ExamSlammrUserProfileTestDataFactory {

	companion object {


		/**
		 * @return a User Profile Item Builder containing a valid user profile item, ready to have it's build method called
		 */
		@JvmStatic
		fun mrBurnsExamSlammrUserProfile(): ExamSlammrUserProfile.ExamSlammrUserProfileBuilder {
			return ExamSlammrUserProfile.builder()
					.id("12345")
					.compositeUserId("twitter:09876")
					.firstname("Montgomery")
					.surname("Burns")
					.nickname("Mr Burns")
					.email("monty.burns@springfield.com")
					.profilePictureUrl("http://profile.pics/monty.burns")
					.roles(newHashSet("USER"))
					.registrationDateTime(ZonedDateTime.parse("2018-01-20T12:20:30.123Z", ISO_ZONED_DATE_TIME))
					.lastLogonDateTime(ZonedDateTime.parse("2018-05-09T08:12:43.456Z", ISO_ZONED_DATE_TIME))
		}

		/**
		 * @return a User Profile Item Builder containing a valid user profile item, ready to have it's build method called
		 */
		@JvmStatic
		fun smithersExamSlammrUserProfile(): ExamSlammrUserProfile.ExamSlammrUserProfileBuilder {
			return ExamSlammrUserProfile.builder()
					.id("67890")
					.compositeUserId("facebook:54321")
					.firstname("Waylon")
					.surname("Smithers")
					.nickname("Smithers")
					.email("waylon.smithers@springfield.com")
					.profilePictureUrl("http://profile.pics/waylon.smithers")
					.roles(newHashSet("USER"))
					.registrationDateTime(ZonedDateTime.parse("2018-01-20T12:20:30.123Z", ISO_ZONED_DATE_TIME))
					.lastLogonDateTime(ZonedDateTime.parse("2018-05-09T08:12:43.456Z", ISO_ZONED_DATE_TIME))
		}
	}
}