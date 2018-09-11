package uk.co.claritysoftware.exam.slammr.webapp.persistence.config;

import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import uk.co.claritysoftware.exam.slammr.webapp.config.ApplicationConfig;

/**
 * Spring Boot configuration class for AWS DynamoDB concerns
 */
@Configuration
@EnableDynamoDBRepositories(basePackages = "uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.repository")
public class DynamoDbConfiguration {

	private final ApplicationConfig applicationConfig;

	private final Regions region;

	@Autowired
	public DynamoDbConfiguration(ApplicationConfig applicationConfig) {
		this.applicationConfig = applicationConfig;
		this.region = Regions.valueOf(applicationConfig.getAwsRegion());
	}

	@Bean
	public AWSCredentialsProvider credentialsProvider() {
		BasicAWSCredentials awsCredentials = new BasicAWSCredentials(applicationConfig.getAwsAccessKeyId(), applicationConfig.getAwsSecretKey());
		return new AWSStaticCredentialsProvider(awsCredentials);
	}

	@Bean
	public AmazonDynamoDB amazonDynamoDB(AWSCredentialsProvider credentialsProvider) {
		AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder.standard()
				.withCredentials(credentialsProvider)
				.withRegion(region)
				.build();

		if (applicationConfig.getAwsDynamoDbEndpoint().isPresent()) {
			amazonDynamoDB.setEndpoint(applicationConfig.getAwsDynamoDbEndpoint().get());
		}

		return amazonDynamoDB;
	}
}