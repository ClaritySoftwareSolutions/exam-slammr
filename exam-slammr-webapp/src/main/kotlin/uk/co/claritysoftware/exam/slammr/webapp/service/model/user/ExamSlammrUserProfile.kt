package uk.co.claritysoftware.exam.slammr.webapp.service.model.user

import java.time.ZonedDateTime

/**
 * Pojo defining an Exam Slammr User Profile
 */
data class ExamSlammrUserProfile(
		val id: String? = null,

		val compositeUserId: String? = null,

		val firstname: String? = null,

		val surname: String? = null,

		val nickname: String? = null,

		val email: String? = null,

		val profilePictureUrl: String? = null,

		val roles: Set<String>? = null,

		val registrationDateTime: ZonedDateTime? = null,

		val lastLogonDateTime: ZonedDateTime? = null
)