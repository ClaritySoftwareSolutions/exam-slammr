package uk.co.claritysoftware.exam.slammr.lambda.user.handler;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import uk.co.claritysoftware.exam.slammr.lambda.user.dto.Credentials;

import lombok.extern.slf4j.Slf4j;

/**
 * Lambda {@link RequestHandler} for registering a new user
 */
@Slf4j
public class RegisterNewUserRequestHandler implements RequestHandler<Credentials, Boolean>{

	private static final Regions REGION = Regions.EU_WEST_2;

	@Override
	public Boolean handleRequest(Credentials credentials, Context context) {
		log.debug("Check user registered with Credentials {} with Context {}", credentials, context);

		AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
				.withRegion(REGION)
				.build();

		DynamoDBMapper mapper = new DynamoDBMapper(client);

		//mapper.query();

		/**
		 *
		 * 		log.debug("Lambda invoked with input {} and Context {}", input, context);

		 Map<String, Object> response = new HashMap<>();
		 response.put("isBase64Encoded", false);
		 response.put("statusCode", 200);
		 response.put("headers", new HashMap());
		 response.put("body",
		 String.format("identityId: '%s', identityPoolId '%s'", context.getIdentity().getIdentityId(), context.getIdentity().getIdentityPoolId()));

		 log.debug("Lamdda response {}", response);

		 *
		 */


		return null;
	}
}
