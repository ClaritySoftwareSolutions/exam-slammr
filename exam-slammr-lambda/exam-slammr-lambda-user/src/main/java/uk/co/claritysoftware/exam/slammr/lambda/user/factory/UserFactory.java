package uk.co.claritysoftware.exam.slammr.lambda.user.factory;

import uk.co.claritysoftware.exam.slammr.lambda.user.dto.UserRegistrationRequest;
import uk.co.claritysoftware.exam.slammr.lambda.user.dynamodb.items.User;

/**
 * Factory class to return instances of {@link User}
 */
public class UserFactory {

    /**
     * Creates a {@link User} from a web federated user id and a {@link UserRegistrationRequest}
     *
     * @param webFederatedUserId
     * @param userRegistrationRequest
     * @return
     */
    public static User valueOf(String webFederatedUserId, UserRegistrationRequest userRegistrationRequest) {
        return User.builder()
                .webFederatedUserId(webFederatedUserId)
                .name(userRegistrationRequest.getSocialIdentity().getName())
                .email(userRegistrationRequest.getSocialIdentity().getEmail())
                .profilePictureUrl(userRegistrationRequest.getSocialIdentity().getPicture().getUrl())
                .identityProvider(userRegistrationRequest.getSocialIdentityProvider().name())
                .build();
    }
}
