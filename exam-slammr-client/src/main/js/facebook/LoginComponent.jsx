import React from 'react';

class LoginComponent extends React.Component {
    constructor(props) {
        super(props);
        this.state = {};

        this.handleFBLogin = this.handleFBLogin.bind(this);
        this.checkLoginState = this.checkLoginState.bind(this);
        this.statusChangeCallback = this.statusChangeCallback.bind(this);
        this.testAPI = this.testAPI.bind(this);
    }



    testAPI() {
        console.log('Welcome!  Fetching your information.... ');
        FB.api('/me', (response) => {
            console.log('Successful login for: ' + response.name);
            console.log("response", response)
        });
    }

    statusChangeCallback(response) {
        console.log('statusChangeCallback');
        console.log(response);
        if (response.status === 'connected') {
            this.testAPI();
        } else if (response.status === 'not_authorized') {
            console.log("Please log into this app.");
        } else {
            console.log("Please log into this facebook.");
        }
    }

    checkLoginState() {
        FB.getLoginStatus((response) => {
            this.statusChangeCallback(response);
        });
    }

    handleFBLogin() {
        FB.login(this.checkLoginState());
    }

    render() {
        return (
            <button
                classNames="btn-facebook"
                id="btn-social-login"
                onClick={this.handleFBLogin}
            >
                <span className="fa fa-facebook"></span> Sign in with Facebook
            </button>
        );
    }
}

export default LoginComponent;