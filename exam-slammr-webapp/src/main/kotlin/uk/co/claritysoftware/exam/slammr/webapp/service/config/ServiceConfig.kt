package uk.co.claritysoftware.exam.slammr.webapp.service.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.repository.DynamoDbQuestionItemRepository
import uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.repository.DynamoDbUserProfileItemRepository
import uk.co.claritysoftware.exam.slammr.webapp.service.QuestionService
import uk.co.claritysoftware.exam.slammr.webapp.service.UserProfileService

/**
 * Spring Configuration class for Service layer
 */
@Configuration
open class ServiceConfig {

  @Bean
  open fun userProfileServic(userProfileItemRepository: DynamoDbUserProfileItemRepository): UserProfileService {
    return UserProfileService(userProfileItemRepository)
  }

  @Bean
  open fun questionService(questionItemRepository: DynamoDbQuestionItemRepository): QuestionService {
    return QuestionService(questionItemRepository)
  }
}
