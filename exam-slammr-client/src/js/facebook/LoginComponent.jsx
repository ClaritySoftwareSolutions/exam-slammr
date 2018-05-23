import React from 'react';

import {facebookLogin, facebookLogout} from './FacebookApi.js'

class LoginComponent extends React.Component {
    constructor(props) {
        super(props);
        this.state = {};

        this.login = this.login.bind(this);
        this.logout = this.logout.bind(this);
    }

    login() {
        facebookLogin().then((response) => {
            console.log("login response", response)
            this.props.loginStatusChangeHandler(response);
        });
    }

    logout() {
        facebookLogout().then((response) => {
            console.log("logout response", response)
            this.props.loginStatusChangeHandler(response);
        });
    }

    render() {
        let button = undefined;
        if (this.props.fbLoginStatus.status === 'connected') {
            button = <button onClick={this.logout}>Logout</button>
        } else {
            button = <button onClick={this.login}>Login</button>
        }
        return (
            button
        );
    }
}

export default LoginComponent;