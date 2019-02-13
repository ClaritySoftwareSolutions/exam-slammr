package uk.co.claritysoftware.exam.slammr.webapp.service.model.question

import java.time.ZonedDateTime

/**
 * Pojo defining an Exam Slammr Question
 */
data class Question(val id: String?,
					val slug: String?,
					val summary: String,
					val questionText: String,
					val answers: Set<AnswerOption>,
					val tags: Set<String>,
					val certifications: Set<String>,
					val furtherReadings: Set<FurtherReading>,
					val createdBy: String,
					val createdDateTime: ZonedDateTime,
					val updatedBy: String?,
					val updatedDateTime: ZonedDateTime?,
					val status: QuestionStatus,
					val votes: Int)