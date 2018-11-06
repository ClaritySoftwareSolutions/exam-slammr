package uk.co.claritysoftware.exam.slammr.webapp.web.model.question

import nl.jqno.equalsverifier.EqualsVerifier
import nl.jqno.equalsverifier.Warning
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Unit test class for [FurtherReading]
 */
class FurtherReadingTest {

	@Test
	fun shouldGenerateEmptyFurtherReading() {
		// Given
		val expectedFurtherReading = FurtherReading.builder()
				.description(null)
				.referenceLocation(null)
				.build()

		// When
		val furtherReading = FurtherReading.generateEmptyFurtherReading()

		// Then
		assertThat(furtherReading).isEqualTo(expectedFurtherReading)
	}

	@Test
	fun shouldHonourEqualsHashcodeContract() {
		EqualsVerifier.forClass(FurtherReading::class.java)
				.suppress(Warning.NONFINAL_FIELDS)
				.verify()
	}

}