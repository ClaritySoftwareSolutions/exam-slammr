package uk.co.claritysoftware.exam.slammr.webapp.web.exception

import uk.co.claritysoftware.exam.slammr.webapp.service.model.user.ExamSlammrUserProfile

/**
 * RuntimeException for when an [ExamSlammrUserProfile] can not be found
 */
class ExamSlammrUserProfileNotFoundException(userId: String) : RuntimeException("ExamSlammrUserProfile with id $userId not found")
