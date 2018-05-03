/**
 * A series of functions to invoke the application's REST API endpoints
 */

import axios from 'axios';
import {generateSigV4Headers} from '../aws/AwsSignatureV4.js';
import AWS from "aws-sdk/index";

/**
 * Register a new user
 */
export function registerNewUser(federatedWebIdentity, registerUserRequest) {

    let sessionToken = federatedWebIdentity.SessionToken;
    let accessKey = federatedWebIdentity.AccessKeyId;
    let secretKey = federatedWebIdentity.SecretKey;

    let opts = {
        'service': 'execute-api',
        'region': 'eu-west-2',
        'host': 'g8wh8brpud.execute-api.eu-west-2.amazonaws.com',
        'path': '/prod',
        accessKey,
        secretKey,
        sessionToken
    };

    let headers = generateSigV4Headers(opts)
    console.log("headers", headers)
    axios.post('https://g8wh8brpud.execute-api.eu-west-2.amazonaws.com/prod',
        registerUserRequest,
        {
            headers: headers
        })
        .then(response => console.log("response: ", response))
        .catch(error => console.log(error));

}