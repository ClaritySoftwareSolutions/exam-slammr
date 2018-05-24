/**
 * Request handler to get a user profile
 */
const AWS = require('aws-sdk');

const DYNAMODB_TABLE_NAME = 'exam-slammr-users';


exports.handle = ((event, context, callback) => {

    if (!context.identity || !context.identity.cognitoIdentityId) {
        callback(null, {
            statusCode: 401,
            body: 'Must be called with a Cognito Identity'
        });
        return;
    }

    let identityId = context.identity.cognitoIdentityId;

    let params = {
        TableName: DYNAMODB_TABLE_NAME,
        ConditionExpression: 'webFederatedUserId = :webFederatedUserId',
        ExpressionAttributeValues: {':webFederatedUserId' : {'S': identityId}}
    };

    AWS.config.update({region: 'eu-west-2'});
    let db_client = new AWS.DynamoDB({apiVersion: '2012-10-08'});
    db_client.getItem(params, (error, data) => {
        if (error) {
            console.error(error);
            callback(null, {
                statusCode: error.statusCode,
                body: error.message
            });
            return;
        }

        callback(null, {
            statusCode: 200,
            body: data
        })
    });

});