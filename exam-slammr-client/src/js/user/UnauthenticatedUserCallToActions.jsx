/*
React component for the calls to action for an unauthenticated user
 */

import React, { Component } from 'react';

class UnauthenticatedUserCallToActions extends Component {

    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div className="unauthenticated-ctas">
                <a className="login" href="/#/login">
                    Login
                </a>
                <a className="register" href="/#/registration">
                    Register
                </a>
            </div>
        );
    }
}

export default UnauthenticatedUserCallToActions;