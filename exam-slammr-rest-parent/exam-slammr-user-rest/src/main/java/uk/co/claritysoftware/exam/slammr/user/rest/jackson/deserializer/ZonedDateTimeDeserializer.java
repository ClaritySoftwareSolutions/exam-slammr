package uk.co.claritysoftware.exam.slammr.user.rest.jackson.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.ZonedDateTime;

import static java.time.format.DateTimeFormatter.ISO_ZONED_DATE_TIME;

/**
 * Jackson deserializer to deserialize values into {@link ZonedDateTime} instances in a controller manner
 */
public class ZonedDateTimeDeserializer extends StdDeserializer<ZonedDateTime> {

    public ZonedDateTimeDeserializer() {
        this(null);
    }

    private ZonedDateTimeDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public ZonedDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String serializedZonedDateTime = p.getValueAsString();
        return ZonedDateTime.parse(serializedZonedDateTime, ISO_ZONED_DATE_TIME);
    }
}
