/**
 * Unit test class for RegisterNewUserRequestHandler
 */
const chai = require('chai');
const sinon = require('sinon');
const sinonChai = require("sinon-chai");
chai.should();
chai.use(sinonChai);

const handler = require('../src/RegisterNewUserRequestHandler');
const AWS = require('aws-sdk-mock');

describe('Unit tests for RegisterNewUserRequestHandler', () => {

    let event, context, callback, clock, expectedPutItemParams;

    before('fix date and time so moment is predictable', () => {
        const marchEpochTime = Math.round(new Date(2018, 4, 22, 21, 35, 0).getTime());
        clock = sinon.useFakeTimers();
        clock.tick(marchEpochTime);
    });

    after('reset the clock', () => {
        clock.restore();
    });

    beforeEach('setup', () => {
        event = {
            body: JSON.stringify({
                socialIdentityProvider: 'facebook',
                userProfile: {
                    name: 'A Person',
                    email: 'a.person@email.com',
                    profilePicture: {
                        url: 'http://host.com/profilePic.png'
                    }
                }
            })
        };
        context = {
            identity: {
                cognitoIdentityId: 'cognito-id'
            }
        };
        callback = sinon.spy();
        expectedPutItemParams = {
            ConditionExpression: "webFederatedUserId <> :webFederatedUserId",
            ExpressionAttributeValues: { ':webFederatedUserId': { S: "cognito-id" } },
            Item: {
                createdDate: { S: "2018-05-22T21:35:00+01:00" },
                email: { S: "a.person@email.com" },
                identityProvider: { S: "facebook" },
                name: { S: "A Person" },
                profilePictureUrl: { S: "http://host.com/profilePic.png" },
                webFederatedUserId: { S: "cognito-id" }
            },
            TableName: "exam-slammr-users"
        };
    });

    afterEach('reset AWS mocks', () => {
        AWS.restore()
    });

    it('should fail to handle given no identity', () => {
        // Given
        context = {};
        let expectedCallBackObject = { body: "Must be called with a Cognito Identity", statusCode: 401 };

        // When
        handler.handle(event, context, callback);

        // Then
        callback.should.have.been.calledWith(null, expectedCallBackObject);
    });

    it('should fail to handle given no cognitoIdentityId', () => {
        // Given
        context = {
            identity: {}
        };
        let expectedCallBackObject = { body: "Must be called with a Cognito Identity", statusCode: 401 };

        // When
        handler.handle(event, context, callback);

        // Then
        callback.should.have.been.calledWith(null, expectedCallBackObject);
    });

    it('should handle given putItem successful', () => {
        // Given
        let error = null;
        let putItemFn = sinon.spy((params, callback) => {
            callback(error)
        });
        AWS.mock('DynamoDB', 'putItem', putItemFn);

        let expectedCallBackObject = { statusCode: 201 };

        // When
        handler.handle(event, context, callback);

        // Then
        callback.should.have.been.calledWith(null, expectedCallBackObject);
        putItemFn.should.have.been.calledWith(expectedPutItemParams);
    });

    it('should fail to handle given putItem fails because of ConditionalCheckFailedException', () => {
        // Given
        let error = {
            code: 'ConditionalCheckFailedException'
        };
        let putItemFn = sinon.spy((params, callback) => {
            callback(error)
        });
        AWS.mock('DynamoDB', 'putItem', putItemFn);

        let expectedCallBackObject = {
            statusCode: 409,
            body: 'User already registered'
        };

        // When
        handler.handle(event, context, callback);

        // Then
        callback.should.have.been.calledWith(null, expectedCallBackObject);
        putItemFn.should.have.been.calledWith(expectedPutItemParams);
    });

    it('should fail to handle given putItem fails for other reason', () => {
        // Given
        let error = {
            statusCode: 501,
            message: 'Some unexpected error'
        };
        let putItemFn = sinon.spy((params, callback) => {
            callback(error)
        });
        AWS.mock('DynamoDB', 'putItem', putItemFn);

        let expectedCallBackObject = {
            statusCode: 501,
            body: 'Some unexpected error'
        };

        // When
        handler.handle(event, context, callback);

        // Then
        callback.should.have.been.calledWith(null, expectedCallBackObject);
        putItemFn.should.have.been.calledWith(expectedPutItemParams);
    });
});

