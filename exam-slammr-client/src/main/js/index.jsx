import React, { Component } from 'react';
import {render} from 'react-dom';
import { HashRouter as Router, Route } from 'react-router-dom';

import {facebookLoginStatus, facebookMe, loadFaceBookApi} from "./facebook/FacebookApi.js";

import 'bootstrap/dist/css/bootstrap.min.css'
import '../scss/main.scss';
import AWS from 'aws-sdk';
import HomepageComponent from "./pages/HomepageComponent.jsx";
import LoginPageComponent from "./pages/LoginPageComponent.jsx";
import RegistrationPageComponent from "./pages/RegistrationPageComponent.jsx";

class ExamSlammrlApp extends Component {

    constructor(props) {
        super(props);
        this.state = {
            fbSdkLoaded: false,
            awsCognitoFederatedIdentityToken: undefined
        };

        this.setCognitoToken = this.setCognitoToken.bind(this);
    }

    componentDidMount() {
        const APP_ID = '302525613607160';
        loadFaceBookApi(APP_ID).then(() => {
            this.setState({'fbSdkLoaded': true});
        });
    }

    setCognitoToken(token) {
        this.setState({'awsCognitoFederatedIdentityToken': token});
    }

    render() {
        return (
            <Router>
                <div>
                    <Route exact path="/" component={HomepageComponent}/>
                    <Route path="/login" component={LoginPageComponent}/>
                    <Route path="/registration" render={() => <RegistrationPageComponent authTokenHandler={this.setCognitoToken}/>}/>
                </div>
            </Router>
        );
    }
}

render(
    <ExamSlammrlApp/>,
    document.getElementById('app')
);
