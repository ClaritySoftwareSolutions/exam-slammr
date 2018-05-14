package uk.co.claritysoftware.exam.slammr.lambda.user.handler;

//import javax.inject.Inject;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.common.base.Preconditions;
import uk.co.claritysoftware.exam.slammr.lambda.user.dto.UserProfile;
import uk.co.claritysoftware.exam.slammr.lambda.user.dto.UserRegistrationRequest;

import lombok.extern.slf4j.Slf4j;

//import uk.co.claritysoftware.exam.slammr.lambda.user.dagger.DaggerLambdaComponent;

/**
 * {@link RequestHandler} for retrieving the user profile of the authenticated user
 */
@Slf4j
public class GetUserRequestHandler implements RequestHandler<Void, UserProfile> {

//	@Inject
	protected DynamoDBMapper dynamoDBMapper;

	public GetUserRequestHandler() {
//		LambdaComponent appComponent = DaggerLambdaComponent.builder().build();
//		this.dynamoDBMapper = appComponent.getDBMapper();
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Lambda function to read a {@link UserRegistrationRequest} from the request, and use it to save a new user
	 * item in dynamodb
	 *
	 * @throws IllegalArgumentException if the Context does not contain a Cognito Identity
	 */
	@Override
	public UserProfile handleRequest(Void aVoid, Context context) {
		String identityId = context.getIdentity().getIdentityId();
		Preconditions.checkArgument(identityId != null && identityId.length() > 0, "Must be called with a Cognito Identity");

		log.debug("Retrieve User with identityId {}", identityId);

		UserProfile userProfile = dynamoDBMapper.load(UserProfile.class, identityId);

		log.debug("retrieved profile {}", userProfile);

		return userProfile;
	}
}
