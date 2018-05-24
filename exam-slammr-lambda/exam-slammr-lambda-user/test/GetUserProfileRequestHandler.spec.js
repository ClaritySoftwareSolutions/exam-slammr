/**
 * Unit test class for GetUserProfileRequestHandler
 */
const chai = require('chai');
const sinon = require('sinon');
const sinonChai = require("sinon-chai");
chai.should();
chai.use(sinonChai);

const handler = require('../src/GetUserProfileRequestHandler');
const AWS = require('aws-sdk-mock');

describe('Unit tests for GetUserProfileRequestHandler', () => {

    let event, context, callback;

    beforeEach('setup', () => {
        event = {};
        context = {
            identity: {
                cognitoIdentityId: 'cognito-id'
            }
        };
        callback = sinon.spy();
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

});

