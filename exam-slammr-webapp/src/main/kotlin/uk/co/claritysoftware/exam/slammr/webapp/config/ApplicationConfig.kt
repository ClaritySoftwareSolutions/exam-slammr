package uk.co.claritysoftware.exam.slammr.webapp.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

/**
 * Simple bean encapsulating the application configuration properties
 */
@Component
class ApplicationConfig {

  @Value("\${aws.region}")
  val awsRegion: String? = null

  @Value("\${aws.accessKeyId}")
  val awsAccessKeyId: String? = null

  @Value("\${aws.secretKey}")
  val awsSecretKey: String? = null

  @Value("\${aws.dynamodb.endpoint:#{null}}")
  val awsDynamoDbEndpoint: Optional<String>? = null

}
