import Promise from "promise";
import AWS from "aws-sdk/index";

export function cognitoFederatedWebIdentityLogin(fbLoginStatus) {

    return new Promise((resolve, reject) => {
        // Initialize the Amazon Cognito credentials provider
        AWS.config.region = 'eu-west-2'; // Region
        AWS.config.credentials = new AWS.CognitoIdentityCredentials({
            IdentityPoolId: 'eu-west-2:67eb3767-ab96-4093-9799-f847a239d565',
            Logins: {
                'graph.facebook.com': fbLoginStatus.authResponse.accessToken
            }
        });

        AWS.config.credentials.get((error) => {
            if (error) {
                reject(error);
            } else {
                resolve(AWS.config.credentials);
            }
        });
    });
}