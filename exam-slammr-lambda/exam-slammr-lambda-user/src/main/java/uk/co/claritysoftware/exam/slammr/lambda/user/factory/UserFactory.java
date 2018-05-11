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
                .name(userRegistrationRequest.getUserProfile().getName())
                .email(userRegistrationRequest.getUserProfile().getEmail())
                .profilePictureUrl(userRegistrationRequest.getUserProfile().getPicture().getUrl())
                .identityProvider(userRegistrationRequest.getSocialIdentityProvider().name())
                .build();
    }
}
