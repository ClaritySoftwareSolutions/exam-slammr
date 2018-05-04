package uk.co.claritysoftware.exam.slammr.lambda.user.handler;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import uk.co.claritysoftware.exam.slammr.lambda.user.dto.UserRegistrationRequest;
import uk.co.claritysoftware.exam.slammr.lambda.user.dynamodb.items.User;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static uk.co.claritysoftware.exam.slammr.lambda.user.factory.UserFactory.valueOf;

/**
 * {@link RequestHandler} for registering a new user
 */
@Slf4j
public class RegisterNewUserRequestHandler implements RequestStreamHandler {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static final Regions REGION = Regions.EU_WEST_2;

    /**
     * {@inheritDoc}
     * <p>
     * Lambda function to read a {@link UserRegistrationRequest} from the request, and use it to save a new user
     * item in dynamodb
     *
     * @throws IllegalStateException    if the InputStream cannot be deserialized
     * @throws IllegalArgumentException if the Context does not contain a Cognita Identity
     */
    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) {
        String identityId = context.getIdentity().getIdentityId();
        Validate.notEmpty(identityId, "Must be called with a Cognito Identity");

        UserRegistrationRequest userRegistrationRequest = userRegistrationRequest(inputStream);

        log.debug("Register New User with UserRegistrationRequest {} with identityId {}", userRegistrationRequest, identityId);

        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withRegion(REGION)
                .build();
        DynamoDBMapper mapper = new DynamoDBMapper(client);

        User newUser = valueOf(identityId, userRegistrationRequest);
        mapper.save(newUser);
    }

    private UserRegistrationRequest userRegistrationRequest(InputStream inputStream) {
        try {
            return OBJECT_MAPPER.readValue(inputStream, UserRegistrationRequest.class);
        } catch (IOException e) {
            log.error("Exception deserializing Lambda InputStream into UserRegistrationRequest", e);
            throw new IllegalStateException("Exception deserializing Lambda InputStream into UserRegistrationRequest");
        }
    }

}
