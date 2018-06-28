/**
 * Factory class to return AWS instances
 */
const AWS = require('aws-sdk');
const REGION = {region: 'eu-west-2'};
const API_VERSION = {apiVersion: '2012-10-08'};

class AWSFactory {

    /**
     * Returns a DynamoDB client
     */
    static dynamoDbClient() {
        AWS.config.update(REGION);
        return new AWS.DynamoDB(API_VERSION);
    }
}

module.exports = AWSFactory;