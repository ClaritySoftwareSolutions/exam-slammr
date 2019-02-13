package uk.co.claritysoftware.exam.slammr.webapp.web.model.question

import javax.validation.constraints.NotBlank

/**
 * Encapsulates the data for a Further Reading reference to support a question and answer
 */
data class FurtherReading(@field:NotBlank
						  var description: String = "",

						  var referenceLocation: String = ""
)