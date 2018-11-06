package uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.convertor

import org.junit.Test

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

import org.assertj.core.api.AssertionsForClassTypes.assertThat

/**
 * Unit test class for [ZonedDateTimeConverter]
 */
class ZonedDateTimeConverterTest {

    private val converter = ZonedDateTimeConverter()

    @Test
    fun shouldConvert() {
        // Given
        val expectedValue = "2017-06-29T20:32:12.231Z"
        val zonedDateTime = ZonedDateTime.parse(expectedValue, DateTimeFormatter.ISO_ZONED_DATE_TIME)

        // When
        val convertedValue = converter.convert(zonedDateTime)

        // Then
        assertThat(convertedValue).isEqualTo(expectedValue)
    }

    @Test
    fun shouldUnconvert() {
        // Given
        val value = "2017-06-29T20:32:12.231Z"
        val expectedZonedDateTime = ZonedDateTime.parse(value, DateTimeFormatter.ISO_ZONED_DATE_TIME)

        // When
        val convertedValue = converter.unconvert(value)

        // Then
        assertThat(convertedValue).isEqualTo(expectedZonedDateTime)
    }
}