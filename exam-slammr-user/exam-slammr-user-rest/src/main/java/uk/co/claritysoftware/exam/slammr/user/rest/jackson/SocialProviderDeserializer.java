package uk.co.claritysoftware.exam.slammr.user.rest.jackson;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import uk.co.claritysoftware.exam.slammr.user.rest.model.SocialIdentityProvider;

/**
 * {@link JsonDeserializer} to serialize string values into the corresponding {@link SocialIdentityProvider}
 */
public class SocialProviderDeserializer extends JsonDeserializer<SocialIdentityProvider> {

    @Override
    public SocialIdentityProvider deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
        String value = jsonParser.getValueAsString();
        return SocialIdentityProvider.of(value);
    }
}
