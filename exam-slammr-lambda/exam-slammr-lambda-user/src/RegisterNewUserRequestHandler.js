/**
 * Request handler to register a new user
 */
const AWS = require('aws-sdk');
const moment = require('moment');

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
    let requestBody = JSON.parse(event.body);

    let newUser = {
        'webFederatedUserId': {'S': identityId},
        'name': {'S': requestBody.userProfile.name},
        'email': {'S': requestBody.userProfile.email},
        'identityProvider': {'S': requestBody.socialIdentityProvider},
        'profilePictureUrl': {'S': requestBody.userProfile.profilePicture.url},
        'createdDate': {'S': moment().format()}
    };

    let params = {
        TableName: DYNAMODB_TABLE_NAME,
        ConditionExpression: 'webFederatedUserId <> :webFederatedUserId',
        ExpressionAttributeValues: {':webFederatedUserId' : {'S': identityId}},
        Item: newUser
    };

    AWS.config.update({region: 'eu-west-2'});
    let db_client = new AWS.DynamoDB({apiVersion: '2012-10-08'});
    db_client.putItem(params, (error) => {
        if (error) {
            if (error.code === 'ConditionalCheckFailedException') {
                callback(null, {
                    statusCode: 409,
                    body: 'User already registered'
                });
            } else {
                console.error(error);
                callback(null, {
                    statusCode: error.statusCode,
                    body: error.message
                });
            }
            return;
        }

        callback(null, {
            statusCode: 201
        })
    });

});