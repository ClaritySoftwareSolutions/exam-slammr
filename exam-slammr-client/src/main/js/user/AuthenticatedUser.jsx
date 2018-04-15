/*
React component to represent the authenticated user
 */

import React, { Component } from 'react';

class AuthenticatedUser extends Component {

    constructor(props) {
        super(props)
    }

    render() {
        return (
            <div className="authenticated-user">
                Nathan
            </div>
        );
    }
}

export default AuthenticatedUser;