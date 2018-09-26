package uk.co.claritysoftware.exam.slammr.webapp.config;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

/**
 * Simple bean encapsulating the application configuration properties
 */
@Component
@Getter
public class ApplicationConfig {

	@Value("${aws.region}")
	private String awsRegion;

	@Value("${aws.accessKeyId}")
	private String awsAccessKeyId;

	@Value("${aws.secretKey}")
	private String awsSecretKey;

	@Value("${aws.dynamodb.endpoint:#{null}}")
	private Optional<String> awsDynamoDbEndpoint;

}
