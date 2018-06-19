package uk.co.claritysoftware.exam.slammr.user.rest.model;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.claritysoftware.exam.slammr.user.rest.model.SocialIdentityProvider.FACEBOOK;

import java.io.IOException;
import org.junit.Test;
import com.fasterxml.jackson.databind.ObjectMapper;

import nl.jqno.equalsverifier.EqualsVerifier;

/**
 * Unit test class for {@link UserRegistrationRequest}
 */
public class UserRegistrationRequestTest {

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	@Test
	public void shouldDeserializeGivenAFacebookSocialIdentity() throws IOException {
		// Given
		String json = "" +
				"{" +
				"   \"socialIdentityProvider\":\"facebook\"," +
				"   \"userProfile\":{" +
				"       \"name\":\"A person\"," +
				"       \"email\":\"a.person@email.com\"," +
				"       \"profilePicture\":{" +
				"           \"height\":50," +
				"           \"url\":\"https://picture.url.com\"," +
				"           \"width\":50" +
				"       }," +
				"       \"id\":\"1234567890\"" +
				"   }" +
				"}";

		UserRegistrationRequest expectedRequest = UserRegistrationRequest.builder()
				.socialIdentityProvider(SocialIdentityProvider.FACEBOOK)
				.userProfile(UserProfile.builder()
						.name("A person")
						.email("a.person@email.com")
						.id("1234567890")
						.picture(ProfilePicture.builder()
								.height(50)
								.width(50)
								.url("https://picture.url.com")
								.build())
						.build())
				.build();

		// When
		UserRegistrationRequest userRegistrationRequest = OBJECT_MAPPER.readValue(json, UserRegistrationRequest.class);

		// Then
		assertThat(userRegistrationRequest).isEqualTo(expectedRequest);
	}

	@Test
	public void shouldHonourEqualsHashcodeContract() {
		EqualsVerifier.forClass(UserRegistrationRequest.class)
				.verify();
	}
}