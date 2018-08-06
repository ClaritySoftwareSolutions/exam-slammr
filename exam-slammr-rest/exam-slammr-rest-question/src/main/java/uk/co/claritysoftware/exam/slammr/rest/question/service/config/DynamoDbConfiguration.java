package uk.co.claritysoftware.exam.slammr.rest.question.service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import uk.co.claritysoftware.exam.slammr.rest.question.config.ApplicationConfig;

/**
 * Spring Boot configuration class for AWS DynamoDB concerns
 */
@Configuration
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
    public DynamoDBMapper dynamoDBMapper(AWSCredentialsProvider credentialsProvider) {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withCredentials(credentialsProvider)
                .withRegion(region)
                .build();
        return new DynamoDBMapper(client);
    }
}
