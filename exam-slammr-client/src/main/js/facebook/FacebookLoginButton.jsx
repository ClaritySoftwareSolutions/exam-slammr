/*
React component for a Facebook login button. Once logged in via facebook, it calls AWS Cognito (Federated Web Identity API)
and returns the facebook access token and AWS cognito identity id via the `loginStatusChangeHandler` callback
 */

import React, { Component } from 'react';
import {isFunction} from 'underscore';
import Promise from 'promise';
import AWS from 'aws-sdk';
import {facebookLogin} from './FacebookApi.js';

class FacebookLoginButton extends Component {

    constructor(props) {
        super(props);

        if (!isFunction(this.props.loginStatusChangeHandler)) {
            throw new Error('loginStatusChangeHandler function not provided');
        }

        this.state = {
            facebookLoginError: false,
            awsCognitoError: false
        };

        this.login = this.login.bind(this);
    }

    login() {
        facebookLogin().then((fbLoginStatus) => {
            console.log("login response", fbLoginStatus);
            this.loginToAwsWithWebFederation(fbLoginStatus).then((awsCredentials) => {
                this.props.loginStatusChangeHandler({fbLoginStatus, awsCredentials});
            }).catch((error) => {
                this.setState({awsCognitoError: true});
            });
        }).catch((error) => {
            this.setState({facebookLoginError: true});
        });
    }

    loginToAwsWithWebFederation(fbLoginStatus) {
        // The parameters required to intialize the Cognito Credentials object.
        // If you are authenticating your users through one of the supported
        // identity providers you should set the Logins object with the provider
        // tokens.

        return new Promise((resolve, reject) => {
            // Initialize the Amazon Cognito credentials provider
            AWS.config.region = 'eu-west-2'; // Region
            AWS.config.credentials = new AWS.CognitoIdentityCredentials({
                IdentityPoolId: 'eu-west-2:9a7ba691-61dc-4581-af54-2ba37b3c6c13',
                Logins: {
                    'graph.facebook.com': fbLoginStatus.authResponse.accessToken
                }
            });

            // We can set the get method of the Credentials object to retrieve
            // the unique identifier for the end user (identityId) once the provider
            // has refreshed itself
            AWS.config.credentials.get((error) => {
                if (error) {
                    console.log("Error: " + error);
                    reject(error);
                }

                console.log("Cognito Identity Id: ", AWS.config.credentials);
                resolve(AWS.config.credentials);

                // Other service clients will automatically use the Cognito Credentials provider
                // configured in the JavaScript SDK.
                // var cognitoSyncClient = new AWS.CognitoSync();
                // cognitoSyncClient.listDatasets({
                //     IdentityId: AWS.config.credentials.identityId,
                //     IdentityPoolId: "YOUR_COGNITO_IDENTITY_POOL_ID"
                // }, function(err, data) {
                //     if ( !err ) {
                //         console.log(JSON.stringify(data));
                //     }
                // });
            });
        });
    }

    render() {
        return (
            <div>
                <button onClick={this.login}>Facebook</button>

                { this.state.facebookLoginError ? <p>There was a problem logging in with Facebook</p> : ""}
                { this.state.awsCognitoError ? <p>There was a problem logging into AWS</p> : ""}
            </div>
        );
    }
}

export default FacebookLoginButton;