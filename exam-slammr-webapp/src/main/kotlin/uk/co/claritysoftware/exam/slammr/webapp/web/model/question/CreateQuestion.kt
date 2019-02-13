package uk.co.claritysoftware.exam.slammr.webapp.web.model.question

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

/**
 * Class encapsulating fields within a request to create a new question
 */
data class CreateQuestion(var action: Action? = null,

						  @field:NotBlank(message = "{CreateQuestion.summary.NotBlank.message}")
						  var summary: String = "",

						  @field:NotBlank(message = "{CreateQuestion.question.NotBlank.message}")
						  var question: String = "",

						  @field:Size(min = 2)
						  var answerOptions: MutableList<AnswerOption> = mutableListOf(AnswerOption()),

						  var tags: MutableList<String> = mutableListOf(""),

						  var certifications: MutableList<String> = mutableListOf(""),

						  var furtherReadings: MutableList<FurtherReading> = mutableListOf(FurtherReading())
) {

	enum class Action {
		save, addTag, addCertification, addFurtherReading, addAnswer
	}
}