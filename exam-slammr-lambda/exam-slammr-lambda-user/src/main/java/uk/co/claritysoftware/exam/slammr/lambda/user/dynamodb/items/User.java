package uk.co.claritysoftware.exam.slammr.lambda.user.dynamodb.items;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Builder;
import lombok.Value;

import static uk.co.claritysoftware.exam.slammr.lambda.user.dynamodb.items.User.DYNAMODB_TABLE_NAME;

/**
 * Class representing an exam-slammr user as stored in dynamodb
 */
@Value
@Builder
@DynamoDBTable(tableName=DYNAMODB_TABLE_NAME)
public class User {

    protected static final String DYNAMODB_TABLE_NAME = "exam-slammr-users";

    @DynamoDBHashKey
    private String webFederatedUserId;

    @DynamoDBAttribute
    private String name;

    @DynamoDBAttribute
    private String email;

    @DynamoDBAttribute
    private String identityProvider;

    @DynamoDBAttribute
    private String profilePictureUrl;

}
