/**
 * Repository for UserProfiles
 */
const AWSFactory = require('./AWSFactory');
const DYNAMODB_TABLE_NAME = 'exam-slammr-users';

class UserProfileRepository {

    /**
     * Gets the UserProfile as identified by the identityId, resolved or rejected in a Promise
     * @param identityId the id of the UserProfile
     */
    static getUserProfile(identityId) {
        let dbClient = AWSFactory.dynamoDbClient();
        let queryParams = {
            TableName: DYNAMODB_TABLE_NAME,
            Key: {'webFederatedUserId' : {'S': identityId}}
        };

        return dbClient.getItem(queryParams).promise();
    }

    /**
     * Create and save a new UserProfile to the repository
     */
    static createUserProfile(newUser) {
        let dbClient = AWSFactory.dynamoDbClient();
        let params = {
            TableName: DYNAMODB_TABLE_NAME,
            ConditionExpression: 'webFederatedUserId <> :webFederatedUserId',
            ExpressionAttributeValues: {':webFederatedUserId' : newUser.webFederatedUserId},
            Item: newUser
        };

        return dbClient.putItem(params).promise();
    }
}

module.exports = UserProfileRepository;