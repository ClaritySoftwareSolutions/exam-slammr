package uk.co.claritysoftware.exam.slammr.lambda.question.dynamodb.items;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

import lombok.Builder;
import lombok.Value;

/**
 * @author Nathan Russell
 */
@Value
@Builder
@DynamoDBDocument
public class AnswerDocument {

	@DynamoDBAttribute
	private final String text;

	@DynamoDBAttribute
	private final boolean correct;

}
