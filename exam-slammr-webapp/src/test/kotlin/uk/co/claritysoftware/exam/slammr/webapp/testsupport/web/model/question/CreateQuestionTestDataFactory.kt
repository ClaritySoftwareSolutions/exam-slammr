package uk.co.claritysoftware.exam.slammr.webapp.testsupport.web.model.question

import uk.co.claritysoftware.exam.slammr.webapp.web.model.question.AnswerOption
import uk.co.claritysoftware.exam.slammr.webapp.web.model.question.CreateQuestion
import uk.co.claritysoftware.exam.slammr.webapp.web.model.question.CreateQuestion.Action.save
import uk.co.claritysoftware.exam.slammr.webapp.web.model.question.FurtherReading
import java.util.Arrays.asList

/**
 * Test data factory for [CreateQuestion] instances
 */
class CreateQuestionTestDataFactory {

	companion object {

		/**
		 * @return a CreateQuestion Builder containing a valid question, ready to have it's build method called
		 */
		@JvmStatic
		fun aSimpleCreateQuestionAboutTriangles(): CreateQuestion.CreateQuestionBuilder {
			return CreateQuestion.builder()
					.summary("Triangle sides question")
					.question("How many sides does a triangle have?")
					.answerOptions(asList(AnswerOption.builder()
							.answer("Three")
							.correct(true)
							.build(),
							AnswerOption.builder()
									.answer("Seven")
									.correct(false)
									.build()))
					.furtherReadings(listOf(FurtherReading.builder()
							.description("Basic Maths 101")
							.referenceLocation("http://basic.maths")
							.build()))
					.tags(asList("maths", "geometry"))
					.certifications(listOf("Basic Maths"))
					.action(save)
		}
	}
}