package uk.co.claritysoftware.exam.slammr.lambda.user.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import uk.co.claritysoftware.exam.slammr.lambda.user.dto.SocialIdentityProfilePicture;

import java.io.IOException;

/**
 * {@link JsonDeserializer} to serialize a node into a {@link SocialIdentityProfilePicture} instance by effectively flattening
 * out the `data` node.
 */
public class SocialIdentityProfilePictureDeserializer extends JsonDeserializer<SocialIdentityProfilePicture> {

    @Override
    public SocialIdentityProfilePicture deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {

        JsonNode rootNode = jsonParser.getCodec().readTree(jsonParser);
        JsonNode pictureData = rootNode.get("data");

        return SocialIdentityProfilePicture.builder()
                .height(pictureData.get("height").asInt())
                .width(pictureData.get("width").asInt())
                .url(pictureData.get("url").asText())
                .build();

    }
}
