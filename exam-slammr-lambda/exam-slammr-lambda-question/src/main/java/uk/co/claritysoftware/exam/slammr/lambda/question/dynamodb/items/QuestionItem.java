package uk.co.claritysoftware.exam.slammr.lambda.question.dynamodb.items;

import java.util.List;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import lombok.Builder;
import lombok.Value;

/**
 * Pojo encapsulating the date for a dynamodb Question item
 */
@Value
@Builder
@DynamoDBTable(tableName=QuestionItem.DYNAMODB_TABLE_NAME)
public class QuestionItem {

	protected static final String DYNAMODB_TABLE_NAME = "exam-slammr-questions";

	@DynamoDBHashKey
	private final String id;

	@DynamoDBAttribute
	private final String questionText;

	@DynamoDBAttribute
	private final List<AnswerDocument> answers;

	@DynamoDBAttribute
	private final List<String> tags;

	@DynamoDBAttribute
	private List<String> certifications;

	@DynamoDBAttribute
	private final List<FurtherReadingDocument> furtherReadings;

}
