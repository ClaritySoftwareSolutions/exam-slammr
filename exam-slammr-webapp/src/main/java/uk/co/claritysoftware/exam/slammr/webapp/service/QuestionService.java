package uk.co.claritysoftware.exam.slammr.webapp.service;

import uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.repository.DynamoDbQuestionItemRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Service for Question related operations
 */
@Slf4j
public class QuestionService {

	private final DynamoDbQuestionItemRepository questionItemRepository;

	public QuestionService(DynamoDbQuestionItemRepository questionItemRepository) {
		this.questionItemRepository = questionItemRepository;
	}
}
