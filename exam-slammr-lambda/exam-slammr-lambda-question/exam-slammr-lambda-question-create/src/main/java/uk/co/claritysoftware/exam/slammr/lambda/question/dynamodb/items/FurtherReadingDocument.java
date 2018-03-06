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
public class FurtherReadingDocument {

	@DynamoDBAttribute
	private final String description;

	@DynamoDBAttribute
	private final String referenceLocation;

}
