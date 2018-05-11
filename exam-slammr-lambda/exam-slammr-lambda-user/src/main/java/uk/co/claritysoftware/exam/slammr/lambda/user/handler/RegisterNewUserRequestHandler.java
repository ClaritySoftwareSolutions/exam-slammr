package uk.co.claritysoftware.exam.slammr.lambda.user.handler;

import static uk.co.claritysoftware.exam.slammr.lambda.user.factory.UserFactory.valueOf;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.inject.Inject;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import uk.co.claritysoftware.exam.slammr.lambda.user.dagger.DaggerLambdaComponent;
import uk.co.claritysoftware.exam.slammr.lambda.user.dagger.LambdaComponent;
import uk.co.claritysoftware.exam.slammr.lambda.user.dto.UserRegistrationRequest;
import uk.co.claritysoftware.exam.slammr.lambda.user.dynamodb.items.User;

import lombok.extern.slf4j.Slf4j;

/**
 * {@link RequestStreamHandler} for registering a new user
 */
@Slf4j
public class RegisterNewUserRequestHandler implements RequestStreamHandler {

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	@Inject
	protected DynamoDBMapper dynamoDBMapper;

	public RegisterNewUserRequestHandler() {
		LambdaComponent appComponent = DaggerLambdaComponent.builder().build();
		this.dynamoDBMapper = appComponent.getDBMapper();
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Lambda function to read a {@link UserRegistrationRequest} from the request, and use it to save a new user
	 * item in dynamodb
	 *
	 * @throws IllegalStateException           if the InputStream cannot be deserialized
	 * @throws IllegalArgumentException        if the Context does not contain a Cognito Identity
	 * @throws ConditionalCheckFailedException if the user already exists
	 */
	@Override
	public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) {
		String identityId = context.getIdentity().getIdentityId();
		Preconditions.checkArgument(identityId != null && identityId.length() > 0, "Must be called with a Cognito Identity");

		UserRegistrationRequest userRegistrationRequest = userRegistrationRequest(inputStream);

		log.debug("Register New User with UserRegistrationRequest {} with identityId {}", userRegistrationRequest, identityId);

		User newUser = valueOf(identityId, userRegistrationRequest);
		try {
			saveNewUserIfNotExists(newUser);
			writeResponse(newUser.getWebFederatedUserId(), outputStream);
			log.debug("Successfully saved new user");
		} catch (ConditionalCheckFailedException e) {
			log.info("User {} already exists so not saved", newUser);
			throw new IllegalStateException("User " + newUser.getWebFederatedUserId() + " exists", e);
		}
	}

	private UserRegistrationRequest userRegistrationRequest(InputStream inputStream) {
		try {
			return OBJECT_MAPPER.readValue(inputStream, UserRegistrationRequest.class);
		} catch (IOException e) {
			log.error("Exception deserializing Lambda InputStream into UserRegistrationRequest", e);
			throw new IllegalStateException("Exception deserializing Lambda InputStream into UserRegistrationRequest");
		}
	}

	private void writeResponse(String userId, OutputStream outputStream) {
		try {
			OBJECT_MAPPER.writeValue(outputStream, ImmutableMap.<String, String>builder()
					.put("id", userId)
					.build());
		} catch (IOException e) {
			log.error("Exception serializing user id into OutputStream", e);
			throw new IllegalStateException("Exception serializing user id into OutputStream");
		}
	}

	private void saveNewUserIfNotExists(User user) {
		DynamoDBSaveExpression saveExpression = new DynamoDBSaveExpression()
				.withExpectedEntry("webFederatedUserId", new ExpectedAttributeValue(false));
		log.debug("Attempting to save new user {}", user);

		dynamoDBMapper.save(user, saveExpression);
	}
}
