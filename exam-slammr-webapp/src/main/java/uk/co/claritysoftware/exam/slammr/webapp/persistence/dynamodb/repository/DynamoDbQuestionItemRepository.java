package uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.repository;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.item.question.QuestionItem;

/**
 * Spring Data Spring Social repository for {@link QuestionItem}
 */
@EnableScan
public interface DynamoDbQuestionItemRepository extends CrudRepository<QuestionItem, String> {

}
