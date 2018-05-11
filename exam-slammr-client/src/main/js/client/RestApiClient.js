/**
 * A series of functions to invoke the application's REST API endpoints
 */

import axios from 'axios';
import {interceptor} from '../aws/AwsSignatureV4AxiosInterceptor.js';

/**
 * Register a new user
 */
export function registerNewUser(federatedWebIdentity, registerUserRequest) {

    let credentials = federatedWebIdentity.data.Credentials;
    let sessionToken = credentials.SessionToken;
    let accessKey = credentials.AccessKeyId;
    let secretKey = credentials.SecretKey;

    let auth = {
        'secretAccessKey': secretKey,
        'accessKeyId': accessKey,
        'sessionToken': sessionToken
    };

    console.log('registerUserRequest', registerUserRequest)


    axios.interceptors.request.use(interceptor)
    axios.post('https://9cexf0shb6.execute-api.eu-west-2.amazonaws.com/dev/user',
        registerUserRequest,
        {
            headers: {
                'Content-Type': 'application/json;charset=UTF-8'
            },
            awsAuth: auth
        })
        .then(response => console.log("response: ", response))
        .catch(error => console.log(error));
}