/*
React component for the registration page
 */

import React, {Component} from 'react';
import HeaderComponent from "./HeaderComponent.jsx";
import FacebookLoginButton from "../facebook/FacebookLoginButton.jsx";
import {cognitoFederatedWebIdentityLogin} from '../aws/AwsCognitoApi.js';
import {registerNewUser} from '../client/RestApiClient.js'
import {isFunction} from "underscore";

class RegistrationPageComponent extends Component {

    constructor(props) {
        super(props);

        if (!isFunction(this.props.authTokenHandler)) {
            throw new Error('authTokenHandler function not provided');
        }

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

        cognitoFederatedWebIdentityLogin(response.socialLoginStatus).then((federatedWebIdentity) => {
            this.props.authTokenHandler(federatedWebIdentity);

            let registerUserRequest = {
                'socialIdentityProvider': 'facebook',
                'socialIdentity': response.socialPublicProfile
            };

            registerNewUser(federatedWebIdentity, registerUserRequest);

        })
            .catch((error) => {
                this.awsCognitoLoginError(error);
            });
    }


    render() {
        return (
            <div>
                <HeaderComponent/>
                <h2>Register with your social network identity</h2>

                <FacebookLoginButton loginStatusChangeHandler={this.facebookLoginStatusChangeHandler} errorHandler={this.socialIdentityLoginError}/>
                {this.state.socialIdentityLoginError ? <p>There was a problem logging into your social identity</p> : ""}
                {this.state.awsCognitoError ? <p>There was a problem logging into Exam Slammr</p> : ""}
            </div>
        );
    }
}

export default RegistrationPageComponent