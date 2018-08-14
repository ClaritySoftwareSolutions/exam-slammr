package uk.co.claritysoftware.exam.slammr.rest.question.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.claritysoftware.exam.slammr.rest.question.service.dynamodb.QuestionItem;

import java.util.Optional;

/**
 * Service class exposing methods for Question concerns
 */
@Service
@Slf4j
public class QuestionService {

    private final DynamoDBMapper dynamoDBMapper;

    @Autowired
    public QuestionService(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    /**
     * Return the {@link QuestionItem} identified by the specified id, wrapped in an {@link Optional}
     *
     * @param id the id of the question item to return
     * @return an Optional containing the QuestionItem, or empty if not found
     */
    public Optional<QuestionItem> getQuestion(String id) {
        log.debug("Get Question with id HashKey {}", id);
        QuestionItem questionItem = getQuestionFromDynamoDb(id);
        if (questionItem == null) {
            log.info("QuestionItem with id HashKey {} not found", id);
        }
        return Optional.ofNullable(questionItem);
    }

    /**
     * Saves a new QuestionItem record to dynamo
     *
     * @param newQuestionItem the new question item to save
     * @return an Optional containing the id of the new question, or empty if the new question could not be saved
     */
    public Optional<String> createQuestion(QuestionItem newQuestionItem) {
        log.debug("Create Question with QuestionItem {}", newQuestionItem);

        try {
            dynamoDBMapper.save(newQuestionItem);
            return Optional.of(newQuestionItem.getId());

        } catch (AmazonServiceException e) {
            log.error("Attempt to create Question {} failed", newQuestionItem, e);
            return Optional.empty();
        }
    }

    private QuestionItem getQuestionFromDynamoDb(String id) {
        return dynamoDBMapper.load(QuestionItem.class, id);
    }
}
