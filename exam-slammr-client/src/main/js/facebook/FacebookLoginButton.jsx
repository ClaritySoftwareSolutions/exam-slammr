/*
React component for a Facebook login button. Once logged in via facebook, it calls AWS Cognito (Federated Web Identity API)
and returns the facebook access token and AWS cognito identity id via the `loginStatusChangeHandler` callback
 */

import React, { Component } from 'react';
import {isFunction} from 'underscore';
import {facebookLogin, facebookMe} from './FacebookApi.js';

class FacebookLoginButton extends Component {

    constructor(props) {
        super(props);

        if (!isFunction(this.props.loginStatusChangeHandler)) {
            throw new Error('loginStatusChangeHandler function not provided');
        }
        if (!isFunction(this.props.errorHandler)) {
            throw new Error('errorHandler function not provided');
        }

        this.login = this.login.bind(this);
    }

    login() {
        facebookLogin().then((fbLoginStatus) => {

            facebookMe().then((fbProfile) => {
                this.props.loginStatusChangeHandler({'socialLoginStatus': fbLoginStatus,'socialPublicProfile': fbProfile});

            }).catch((error) => {
                this.props.errorHandler(error);
            });
        }).catch((error) => {
            this.props.errorHandler(error);
        });
    }

    render() {
        return (
            <div>
                <button onClick={this.login}>Facebook</button>
            </div>
        );
    }
}

export default FacebookLoginButton;