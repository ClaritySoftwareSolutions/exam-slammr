/*
React component for the login page
 */

import React, { Component } from 'react';
import HeaderComponent from "./HeaderComponent.jsx";

class LoginPageComponent extends Component {

    constructor(props) {
        super(props)
    }

    render() {
        return (
            <div>
                <HeaderComponent/>
                <h2>Login with your social network identity</h2>

                <div className="fb-login-button" data-max-rows="1" data-size="medium" data-button-type="login_with" data-show-faces="false" data-auto-logout-link="false" data-use-continue-as="false"></div>
            </div>
        );
    }
}

export default LoginPageComponent