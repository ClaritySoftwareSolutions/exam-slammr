package uk.co.claritysoftware.exam.slammr.lambda.user.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.claritysoftware.exam.slammr.lambda.user.dto.SocialIdentityProvider.FACEBOOK;

public class UserRegistrationRequestTest {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Test
    public void shouldDeserializeGivenAFacebookSocialIdentity() throws IOException {
        // Given
        String json = "" +
                "{" +
                "   \"socialIdentityProvider\":\"facebook\"," +
                "   \"socialIdentity\":{" +
                "       \"name\":\"A person\"," +
                "       \"email\":\"a.person@email.com\"," +
                "       \"picture\":{" +
                "           \"data\":{" +
                "               \"height\":50," +
                "               \"is_silhouette\":false," +
                "               \"url\":\"https://picture.url.com\"," +
                "               \"width\":50" +
                "           }" +
                "       }," +
                "       \"id\":\"1234567890\"" +
                "   }" +
                "}";

        UserRegistrationRequest expectedRequest = UserRegistrationRequest.builder()
                .socialIdentityProvider(FACEBOOK)
                .socialIdentity(SocialIdentity.builder()
                        .name("A person")
                        .email("a.person@email.com")
                        .id("1234567890")
                        .picture(SocialIdentityProfilePicture.builder()
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
}