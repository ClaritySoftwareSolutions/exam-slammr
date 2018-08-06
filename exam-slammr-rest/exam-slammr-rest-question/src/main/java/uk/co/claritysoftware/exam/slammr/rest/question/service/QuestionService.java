package uk.co.claritysoftware.exam.slammr.rest.question.service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
