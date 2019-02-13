package uk.co.claritysoftware.exam.slammr.webapp.web.model.question

import javax.validation.constraints.NotBlank

/**
 * Encapsulates the data for a possible answer of a question
 */
data class AnswerOption(@field:NotBlank
						var answer: String = "",

						var correct: Boolean = false)