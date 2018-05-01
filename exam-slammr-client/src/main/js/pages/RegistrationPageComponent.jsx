/*
React component for the registration page
 */

import React, {Component} from 'react';
import HeaderComponent from "./HeaderComponent.jsx";
import FacebookLoginButton from "../facebook/FacebookLoginButton.jsx";
import axios from 'axios';
import {generateSigV4Headers} from '../aws/AwsSignatureV4.js';
import {cognitoFederatedWebIdentityLogin} from '../aws/AwsCognitoApi.js';
import AWS from "aws-sdk/index";

class RegistrationPageComponent extends Component {

    constructor(props) {
        super(props);

        this.state = {
            socialIdentityLoginError: false,
            awsCognitoError: false
        };

        this.facebookLoginStatusChangeHandler = this.facebookLoginStatusChangeHandler.bind(this);
        this.socialIdentityLoginError = this.socialIdentityLoginError.bind(this);
        this.awsCognitoLoginError = this.awsCognitoLoginError.bind(this);
    }

    socialIdentityLoginError(error) {
        console.error('socialIdentityLoginError', error);
        this.setState({socialIdentityLoginError: true});
    }

    awsCognitoLoginError(error) {
        console.error('socialIdentityLoginError', error);
        this.setState({awsCognitoError: true});
    }

    facebookLoginStatusChangeHandler(response) {
        console.log("Facebook response", response);

        cognitoFederatedWebIdentityLogin(response.socialLoginStatus).then((federatedWebIdentity) => {
            console.log("Cognito Identity Id: ", federatedWebIdentity);

        })
        .catch((error) => {
            this.awsCognitoLoginError(error);
        });


/*

        let awsCredentials = response.awsCredentials.data.Credentials;
        let sessionToken = awsCredentials.SessionToken;
        let accessKey = awsCredentials.AccessKeyId;
        let secretKey = awsCredentials.SecretKey;

        let opts = {
            'service': 'execute-api',
            'region': 'eu-west-2',
            'host': 'g8wh8brpud.execute-api.eu-west-2.amazonaws.com',
            'path': '/prod',
            accessKey,
            secretKey,
            sessionToken
        };

        let fbLoginStatus = response.fbLoginStatus;
        let headers = generateSigV4Headers(opts)
        console.log("heasders", headers)
        axios.get('https://g8wh8brpud.execute-api.eu-west-2.amazonaws.com/prod',
            {
                headers: headers
            })
            .then(response => console.log("response: ", response))
            .catch(error => console.log(error));
*/

    }

    render() {
        return (
            <div>
                <HeaderComponent/>
                <h2>Register with your social network identity</h2>

                <FacebookLoginButton loginStatusChangeHandler={this.facebookLoginStatusChangeHandler} errorHandler={this.socialIdentityLoginError}/>
                { this.state.socialIdentityLoginError ? <p>There was a problem logging into your social identity</p> : ""}
                { this.state.awsCognitoError ? <p>There was a problem logging into Exam Slammr</p> : ""}
            </div>
        );
    }
}

export default RegistrationPageComponent