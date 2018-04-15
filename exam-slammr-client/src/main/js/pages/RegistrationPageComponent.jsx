/*
React component for the registration page
 */

import React, { Component } from 'react';
import AWS from 'aws-sdk';
import HeaderComponent from "./HeaderComponent.jsx";
import FacebookLoginButton from "../facebook/FacebookLoginButton.jsx";

class RegistrationPageComponent extends Component {

    constructor(props) {
        super(props);

        this.facebookLoginStatusChangeHandler = this.facebookLoginStatusChangeHandler.bind(this);
    }

    facebookLoginStatusChangeHandler(response) {
        console.log("what to do with this!", response);
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