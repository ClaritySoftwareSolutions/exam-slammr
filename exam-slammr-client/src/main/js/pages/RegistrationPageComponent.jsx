/*
React component for the registration page
 */

import React, {Component} from 'react';
import HeaderComponent from "./HeaderComponent.jsx";
import FacebookLoginButton from "../facebook/FacebookLoginButton.jsx";
import axios from 'axios';
import {generateSigV4Headers} from '../aws/AwsSignatureV4.js';

class RegistrationPageComponent extends Component {

    constructor(props) {
        super(props);

        this.facebookLoginStatusChangeHandler = this.facebookLoginStatusChangeHandler.bind(this);
    }

    facebookLoginStatusChangeHandler(response) {
        console.log("what to do with this!", response);

        let awsCredentials = response.awsCredentials.data.Credentials;
        let sessionToken = awsCredentials.SessionToken;
        let accessKey = awsCredentials.AccessKeyId;
        let secretKey = awsCredentials.SecretKey;

        let opts = {
            'service': 'execute-api',
            'region': 'eu-west-2',
            'host': 'r4i20xwlfb.execute-api.eu-west-2.amazonaws.com',
            'path': '/prod',
            accessKey,
            secretKey,
            sessionToken
        };

        let fbLoginStatus = response.fbLoginStatus;

        axios.get('https://r4i20xwlfb.execute-api.eu-west-2.amazonaws.com/prod',
            {
                headers: generateSigV4Headers(opts)
            })
            .then(response => console.log("response: ", response))
            .catch(error => console.log(error));

    }

    render() {
        return (
            <div>
                <HeaderComponent/>
                <h2>Register with your social network identity</h2>

                <FacebookLoginButton loginStatusChangeHandler={this.facebookLoginStatusChangeHandler}/>
            </div>
        );
    }
}

export default RegistrationPageComponent