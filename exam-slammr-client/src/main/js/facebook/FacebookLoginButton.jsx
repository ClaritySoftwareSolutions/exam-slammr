/*
React component for a Facebook login button. Once logged in via facebook, it calls AWS Cognito (Federated Web Identity API)
 */

import React, { Component } from 'react';
import AWS from 'aws-sdk';
import {facebookLogin} from './FacebookApi.js'

class FacebookLoginButton extends Component {

    constructor(props) {
        super(props);
        this.state = {
            facebookLoginError: false
        };

        this.login = this.login.bind(this);
    }

    login() {
        facebookLogin().then((response) => {
            console.log("login response", response);
            this.loginToAwsWithWebFederation(response);
            // this.props.loginStatusChangeHandler(response);
        }).catch((error) => {
            this.setState({facebookLoginError: true});
        });
    }

    loginToAwsWithWebFederation(fbLoginStatus) {
        // The parameters required to intialize the Cognito Credentials object.
        // If you are authenticating your users through one of the supported
        // identity providers you should set the Logins object with the provider
        // tokens.

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
        AWS.config.credentials.get(function(err) {
            if (err) {
                console.log("Error: "+err);
                return;
            }
            console.log("Cognito Identity Id: " + AWS.config.credentials.identityId);

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
    }

    render() {
        return (
            <div>
                <button onClick={this.login}>Facebook</button>

                { this.state.facebookLoginError ? <p>There was a problem logging in with Facebook</p> : ""}
            </div>
        );
    }
}

export default FacebookLoginButton;