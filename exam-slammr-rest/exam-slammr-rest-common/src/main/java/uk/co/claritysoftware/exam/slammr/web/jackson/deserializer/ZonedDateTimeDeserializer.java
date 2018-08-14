package uk.co.claritysoftware.exam.slammr.web.jackson.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.ZonedDateTime;

import static java.time.format.DateTimeFormatter.ISO_ZONED_DATE_TIME;

/**
 * Jackson deserializer to deserialize values into {@link ZonedDateTime} instances
 */
public class ZonedDateTimeDeserializer extends StdDeserializer<ZonedDateTime> {

    public ZonedDateTimeDeserializer() {
        super(ZonedDateTime.class);
    }

    @Override
    public ZonedDateTime deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
        String serializedZonedDateTime = jsonParser.getValueAsString();
        return ZonedDateTime.parse(serializedZonedDateTime, ISO_ZONED_DATE_TIME);
    }
}
