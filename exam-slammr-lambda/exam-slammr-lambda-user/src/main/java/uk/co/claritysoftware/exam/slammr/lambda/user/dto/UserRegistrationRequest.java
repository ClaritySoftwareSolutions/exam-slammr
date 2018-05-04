package uk.co.claritysoftware.exam.slammr.lambda.user.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Value;
import uk.co.claritysoftware.exam.slammr.lambda.user.jackson.SocialProviderDeserializer;

/**
 * Class encapsulating the social identity data required for a User Registration Request
 */
@Value
@Builder
public class UserRegistrationRequest {

    private SocialIdentityProvider socialIdentityProvider;

    private SocialIdentity socialIdentity;

    @JsonCreator
    private UserRegistrationRequest(@JsonProperty("socialIdentityProvider") @JsonDeserialize(using = SocialProviderDeserializer.class) SocialIdentityProvider socialIdentityProvider,
                                    @JsonProperty("socialIdentity") SocialIdentity socialIdentity) {
        this.socialIdentityProvider = socialIdentityProvider;
        this.socialIdentity = socialIdentity;
    }
}
