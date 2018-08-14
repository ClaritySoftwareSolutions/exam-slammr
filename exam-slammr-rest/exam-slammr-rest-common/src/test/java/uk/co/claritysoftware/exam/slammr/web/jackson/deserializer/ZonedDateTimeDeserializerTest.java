package uk.co.claritysoftware.exam.slammr.web.jackson.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import org.junit.Test;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * Unit test class for {@link ZonedDateTimeDeserializer}
 */
public class ZonedDateTimeDeserializerTest {

    private ZonedDateTimeDeserializer deserializer = new ZonedDateTimeDeserializer();

    @Test
    public void shouldDeserialize() throws IOException {
        // Given
        String serializedZonedDateTime = "2018-07-04T14:06:32.872Z";
        JsonParser jsonParser = mock(JsonParser.class);
        given(jsonParser.getValueAsString()).willReturn(serializedZonedDateTime);

        ZonedDateTime expectedZonedDateTime = ZonedDateTime.parse("2018-07-04T14:06:32.872Z", DateTimeFormatter.ISO_ZONED_DATE_TIME);

        // When
        ZonedDateTime zonedDateTime = deserializer.deserialize(jsonParser, null);

        // Then
        assertThat(zonedDateTime)
                .as("Deserialized ZonedDateTime is as expected")
                .isEqualTo(expectedZonedDateTime);
        assertThat(zonedDateTime.getZone())
                .as("Zone is UTC")
                .isEqualTo(ZoneId.ofOffset("", ZoneOffset.UTC));
    }
}