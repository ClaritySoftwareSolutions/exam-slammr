/*
Parent component to handle user login, registration, and profile concerns
 */

import React, { Component } from 'react';
import AuthenticatedUser from "./AuthenticatedUser.jsx";
import UnauthenticatedUserCallToActions from "./UnauthenticatedUserCallToActions.jsx";

class UserConcern extends Component {

    constructor(props) {
        super(props);
        this.state = {
            userAuthenticated: false
        };
    }

    render() {
        return (
            <div id="user-concern">
                { this.state.userAuthenticated ? <AuthenticatedUser /> : <UnauthenticatedUserCallToActions /> }
            </div>
        );
    }
}

export default UserConcern;