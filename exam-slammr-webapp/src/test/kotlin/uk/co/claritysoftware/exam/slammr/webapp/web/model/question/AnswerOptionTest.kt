package uk.co.claritysoftware.exam.slammr.webapp.web.model.question

import nl.jqno.equalsverifier.EqualsVerifier
import nl.jqno.equalsverifier.Warning
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Unit test class for [AnswerOption]
 */
class AnswerOptionTest {

	@Test
	fun shouldGenerateEmptyAnswerOption() {
		// Given
		val expectedAnswerOption = AnswerOption.builder()
				.answer(null)
				.correct(false)
				.build()

		// When
		val answerOption = AnswerOption.generateEmptyAnswerOption()

		// Then
		assertThat(answerOption).isEqualTo(expectedAnswerOption)
	}

	@Test
	fun shouldHonourEqualsHashcodeContract() {
		EqualsVerifier.forClass(AnswerOption::class.java)
				.suppress(Warning.NONFINAL_FIELDS)
				.verify()
	}

}