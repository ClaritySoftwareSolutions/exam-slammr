package uk.co.claritysoftware.exam.slammr.rest.model;

import java.util.stream.Stream;

/**
 * Enum of Social Identity Providers
 */
public enum SocialIdentityProvider {

    FACEBOOK("facebook");

    private final String value;

    SocialIdentityProvider(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static SocialIdentityProvider of(String value) {
        return Stream.of(SocialIdentityProvider.values())
                .filter(socialIdentityProvider -> socialIdentityProvider.getValue().equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("No SocialIdentityProvider with name %s exists", value)));
    }
}
