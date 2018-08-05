package uk.co.claritysoftware.exam.slammr.lambda.user.handler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doThrow;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.amazonaws.services.lambda.runtime.CognitoIdentity;
import com.amazonaws.services.lambda.runtime.Context;
import uk.co.claritysoftware.exam.slammr.lambda.user.dynamodb.items.User;

/**
 * Unit test class for {@link RegisterNewUserRequestHandler}
 */
@Ignore
@RunWith(MockitoJUnitRunner.class)
public class RegisterNewUserRequestHandlerTest {

	private static final String VALID_REQUEST_PAYLOAD = "" +
			"{" +
			"   \"socialIdentityProvider\":\"facebook\"," +
			"   \"userProfile\":{" +
			"       \"name\":\"A person\"," +
			"       \"email\":\"a.person@email.com\"," +
			"       \"picture\":{" +
			"           \"height\":50," +
			"           \"url\":\"https://picture.url.com\"," +
			"            \"width\":50" +
			"       }," +
			"       \"id\":\"1234567890\"" +
			"   }" +
			"}";

	private static final String IDENTITY_ID = "some-identity-id";

	@Mock
	private DynamoDBMapper dynamoDBMapper;

	@InjectMocks
	private RegisterNewUserRequestHandler handler = new RegisterNewUserRequestHandler();

	@Mock
	private Context context;

	@Mock
	private CognitoIdentity cognitoIdentity;

	@Before
	public void setup() {
		given(context.getIdentity()).willReturn(cognitoIdentity);
		given(cognitoIdentity.getIdentityId()).willReturn(IDENTITY_ID);
	}

	@Test
	public void shouldHandleRequestGivenAValidRequestPayload() {
		// Given
		InputStream inputStream = new ByteArrayInputStream(VALID_REQUEST_PAYLOAD.getBytes());
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		User expectedUser = User.builder()
				.webFederatedUserId(IDENTITY_ID)
				.name("A person")
				.email("a.person@email.com")
				.profilePictureUrl("https://picture.url.com")
				.identityProvider("FACEBOOK")
				.build();

		DynamoDBSaveExpression expectedSaveExpression = new DynamoDBSaveExpression()
				.withExpectedEntry("webFederatedUserId", new ExpectedAttributeValue(false));

		String expectedOutput = "{\"id\":\"" + IDENTITY_ID + "\"}";

		// When
		handler.handleRequest(inputStream, outputStream, context);

		// Then
		then(dynamoDBMapper).should().save(eq(expectedUser), argThat((ArgumentMatcher<DynamoDBSaveExpression>) saveExpression ->
				saveExpression.getExpected().equals(expectedSaveExpression.getExpected())));
		assertThat(outputStream.toString()).isEqualTo(expectedOutput);
	}

	@Test
	public void shouldFailToHandleRequestGivenUserIdAlreadyExists() {
		// Given
		InputStream inputStream = new ByteArrayInputStream(VALID_REQUEST_PAYLOAD.getBytes());
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		User expectedUser = User.builder()
				.webFederatedUserId(IDENTITY_ID)
				.name("A person")
				.email("a.person@email.com")
				.profilePictureUrl("https://picture.url.com")
				.identityProvider("FACEBOOK")
				.build();

		DynamoDBSaveExpression expectedSaveExpression = new DynamoDBSaveExpression()
				.withExpectedEntry("webFederatedUserId", new ExpectedAttributeValue(false));

		ConditionalCheckFailedException exception = new ConditionalCheckFailedException("User exists");
		doThrow(exception).when(dynamoDBMapper).save(any(User.class), any(DynamoDBSaveExpression.class));

		// When
		Throwable throwable = catchThrowable(() -> handler.handleRequest(inputStream, outputStream, context));

		// Then
		then(dynamoDBMapper).should().save(eq(expectedUser), argThat((ArgumentMatcher<DynamoDBSaveExpression>) saveExpression ->
				saveExpression.getExpected().equals(expectedSaveExpression.getExpected())));
		assertThat(throwable).isInstanceOf(IllegalStateException.class)
				.hasMessage("User some-identity-id exists");
	}

	@Test
	public void shouldFailToHandleRequestGivenNoCognitoId() {
		// Given
		InputStream inputStream = new ByteArrayInputStream(VALID_REQUEST_PAYLOAD.getBytes());
		OutputStream outputStream = new ByteArrayOutputStream();
		given(cognitoIdentity.getIdentityId()).willReturn(null);

		// When
		Throwable throwable = catchThrowable(() -> handler.handleRequest(inputStream, outputStream, context));

		// Then
		assertThat(throwable).isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Must be called with a Cognito Identity");
	}

	@Test
	public void shouldFailToHandleRequestGivenInvalidPayload() {
		// Given
		String invalidPayload = "not a valid payload";
		InputStream inputStream = new ByteArrayInputStream(invalidPayload.getBytes());
		OutputStream outputStream = new ByteArrayOutputStream();

		// When
		Throwable throwable = catchThrowable(() -> handler.handleRequest(inputStream, outputStream, context));

		// Then
		assertThat(throwable).isInstanceOf(IllegalStateException.class)
				.hasMessage("Exception deserializing Lambda InputStream into UserRegistrationRequest");
	}
}