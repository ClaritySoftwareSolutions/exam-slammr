package uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.repository

import org.socialsignin.spring.data.dynamodb.repository.EnableScan
import org.springframework.data.repository.CrudRepository
import uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.item.question.QuestionItem

/**
 * Spring Data Spring Social repository for [QuestionItem]
 */
@EnableScan
interface DynamoDbQuestionItemRepository : CrudRepository<QuestionItem, String>
