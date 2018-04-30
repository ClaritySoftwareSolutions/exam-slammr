package uk.co.claritysoftware.exam.slammr.lambda.question.handler;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import uk.co.claritysoftware.exam.slammr.lambda.question.dto.CreateQuestionRequest;
import uk.co.claritysoftware.exam.slammr.lambda.question.dynamodb.items.QuestionItem;
import uk.co.claritysoftware.exam.slammr.lambda.question.factory.QuestionItemFactory;

import lombok.extern.slf4j.Slf4j;

/**
 * Lambda {@link RequestHandler} for creating a new Question
 */
@Slf4j
public class CreateQuestionRequestHandler implements RequestHandler<CreateQuestionRequest, String> {

	private static final Regions REGION = Regions.EU_WEST_2;

	@Override
	public String handleRequest(CreateQuestionRequest createQuestionRequest, Context context) {
		log.debug("Create new Question {} with Context {}", createQuestionRequest, context);

		AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
				.withRegion(REGION)
				.build();

		DynamoDBMapper mapper = new DynamoDBMapper(client);

		QuestionItem questionItem = QuestionItemFactory.of(createQuestionRequest);

		log.debug("Will persist questionItem {} with mapper {}", questionItem, mapper);
		mapper.save(questionItem);

		return questionItem.getId();
	}
}
