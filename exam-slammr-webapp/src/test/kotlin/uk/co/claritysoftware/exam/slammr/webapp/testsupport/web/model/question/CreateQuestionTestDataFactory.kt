package uk.co.claritysoftware.exam.slammr.webapp.testsupport.web.model.question

import uk.co.claritysoftware.exam.slammr.webapp.web.model.question.AnswerOption
import uk.co.claritysoftware.exam.slammr.webapp.web.model.question.CreateQuestion
import uk.co.claritysoftware.exam.slammr.webapp.web.model.question.CreateQuestion.Action.save
import uk.co.claritysoftware.exam.slammr.webapp.web.model.question.FurtherReading

/**
 * Test data factory for [CreateQuestion] instances
 */
class CreateQuestionTestDataFactory {

	companion object {

		/**
		 * @return a CreateQuestion containing a valid question
		 */
		@JvmStatic
		fun aSimpleCreateQuestionAboutTriangles() = CreateQuestion(
				summary = "Triangle sides question",
				question = "How many sides does a triangle have?",
				answerOptions = mutableListOf(AnswerOption("Three", true),
						AnswerOption("Seven", false)),
				furtherReadings = mutableListOf(FurtherReading("Basic Maths 101", "http://basic.maths")),
				tags = mutableListOf("maths", "geometry"),
				certifications = mutableListOf("Basic Maths"),
				action = save)
	}
}