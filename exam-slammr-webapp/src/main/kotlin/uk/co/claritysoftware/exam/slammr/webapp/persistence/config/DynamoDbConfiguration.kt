package uk.co.claritysoftware.exam.slammr.webapp.persistence.config

import com.amazonaws.auth.AWSCredentialsProvider
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.regions.Regions
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import uk.co.claritysoftware.exam.slammr.webapp.config.ApplicationConfig

/**
 * Spring Boot configuration class for AWS DynamoDB concerns
 */
@Configuration
@EnableDynamoDBRepositories(basePackages = arrayOf("uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.repository"))
open class DynamoDbConfiguration
@Autowired
constructor(private val applicationConfig: ApplicationConfig) {

	@Bean
	open fun credentialsProvider(): AWSCredentialsProvider {
		val awsCredentials = BasicAWSCredentials(applicationConfig.awsAccessKeyId!!, applicationConfig.awsSecretKey!!)
		return AWSStaticCredentialsProvider(awsCredentials)
	}

	@Bean
	open fun amazonDynamoDB(credentialsProvider: AWSCredentialsProvider): AmazonDynamoDB {
		val region = Regions.valueOf(applicationConfig.awsRegion!!)
		val amazonDynamoDB = AmazonDynamoDBClientBuilder.standard()
				.withCredentials(credentialsProvider)
				.withRegion(region)
				.build()

		if (applicationConfig.awsDynamoDbEndpoint!!.isPresent) {
			amazonDynamoDB.setEndpoint(applicationConfig.awsDynamoDbEndpoint.get())
		}

		return amazonDynamoDB
	}
}