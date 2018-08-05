package uk.co.claritysoftware.exam.slammr.rest.question.config;

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

}
