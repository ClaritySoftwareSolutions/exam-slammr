package uk.co.claritysoftware.exam.slammr.user.rest.jackson;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import org.junit.Test;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import uk.co.claritysoftware.exam.slammr.user.rest.model.SocialIdentityProvider;

/**
 * Unit test class for {@link SocialProviderDeserializer}
 */
public class SocialProviderDeserializerTest {

	private SocialProviderDeserializer deserializer = new SocialProviderDeserializer();

	@Test
	public void shouldDeserializeGivenValidValue() throws IOException {
		// Given
		JsonParser jsonParser = mock(JsonParser.class);
		DeserializationContext context = mock(DeserializationContext.class);

		given(jsonParser.getValueAsString()).willReturn("facebook");

		// When
		SocialIdentityProvider socialIdentityProvider = deserializer.deserialize(jsonParser, context);

		// Then
		assertThat(socialIdentityProvider).isEqualTo(SocialIdentityProvider.FACEBOOK);
	}

	@Test
	public void shouldFailToDeserializeGivenInvalidValue() throws IOException {
		// Given
		JsonParser jsonParser = mock(JsonParser.class);
		DeserializationContext context = mock(DeserializationContext.class);

		given(jsonParser.getValueAsString()).willReturn("some invalid value");

		// When
		Throwable throwable = catchThrowable(() -> deserializer.deserialize(jsonParser, context));

		// Then
		assertThat(throwable).isInstanceOf(IllegalArgumentException.class)
				.hasMessage("No SocialIdentityProvider with name some invalid value exists");
	}

}