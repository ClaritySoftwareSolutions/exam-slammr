package uk.co.claritysoftware.exam.slammr.webapp.web.model.question

import nl.jqno.equalsverifier.EqualsVerifier
import nl.jqno.equalsverifier.Warning
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.util.Arrays.asList

/**
 * Unit test class for [CreateQuestion]
 */
class CreateQuestionTest {

	@Test
	fun shouldGenerateEmptyCreateQuestion() {
		// Given
		val expectedCreateQuestion = CreateQuestion.builder()
				.action(null)
				.summary(null)
				.question(null)
				.answerOptions(asList(
						AnswerOption.builder().build(),
						AnswerOption.builder().build()
				))
				.tags(listOf(""))
				.certifications(listOf(""))
				.furtherReadings(listOf(FurtherReading.builder().build()))
				.build()

		// When
		val createQuestion = CreateQuestion.generateEmptyCreateQuestion()

		// Then
		assertThat(createQuestion).isEqualTo(expectedCreateQuestion)
	}

	@Test
	fun shouldHonourEqualsHashcodeContract() {
		EqualsVerifier.forClass(CreateQuestion::class.java)
				.suppress(Warning.NONFINAL_FIELDS)
				.verify()
	}

}