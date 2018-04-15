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
            fbLoginStatus: {},
            fbMe: {}
        };

//        this.getFacebookLoginStatus = this.getFacebookLoginStatus.bind(this);
//        this.getFacebookMe = this.getFacebookMe.bind(this);
//        this.updateFbLoginStatus = this.updateFbLoginStatus.bind(this);
//        this.updateAwsCognito = this.updateAwsCognito.bind(this);
    }

    componentDidMount() {
        const APP_ID = '302525613607160';
        loadFaceBookApi(APP_ID).then(() => {
            this.setState({'fbSdkLoaded': true});
        });
    }

/*
    componentDidUpdate(prevProps, prevState) {
        if (this.state.fbSdkLoaded !== prevState.fbSdkLoaded) {
            this.getFacebookLoginStatus();
        }

        if (this.state.fbLoginStatus !== prevState.fbLoginStatus) {
            if (this.state.fbLoginStatus.status === 'connected') {
                this.getFacebookMe();
            } else {
                this.setState({fbMe: {}});
            }
            this.updateAwsCognito(this.state.fbLoginStatus);
        }
    }

    getFacebookLoginStatus() {
        facebookLoginStatus().then((response) => {
            this.updateFbLoginStatus(response);
        })
    }

    getFacebookMe() {
        facebookMe().then((response) => {
            this.setState({fbMe: response});
        })
    }

    updateFbLoginStatus(fbLoginStatus) {
        this.setState({fbLoginStatus});
    }

    updateAwsCognito(fbLoginStatus) {
        // The parameters required to intialize the Cognito Credentials object.
        // If you are authenticating your users through one of the supported
        // identity providers you should set the Logins object with the provider
        // tokens.

        // Initialize the Amazon Cognito credentials provider
        AWS.config.region = 'eu-west-2'; // Region
        AWS.config.credentials = new AWS.CognitoIdentityCredentials({
            IdentityPoolId: 'eu-west-2:9a7ba691-61dc-4581-af54-2ba37b3c6c13',
            Logins: {
                'graph.facebook.com': fbLoginStatus.authResponse.accessToken
            }
        });

        // We can set the get method of the Credentials object to retrieve
        // the unique identifier for the end user (identityId) once the provider
        // has refreshed itself
        AWS.config.credentials.get(function(err) {
            if (err) {
                console.log("Error: "+err);
                return;
            }
            console.log("Cognito Identity Id: " + AWS.config.credentials.identityId);

            // Other service clients will automatically use the Cognito Credentials provider
            // configured in the JavaScript SDK.
            // var cognitoSyncClient = new AWS.CognitoSync();
            // cognitoSyncClient.listDatasets({
            //     IdentityId: AWS.config.credentials.identityId,
            //     IdentityPoolId: "YOUR_COGNITO_IDENTITY_POOL_ID"
            // }, function(err, data) {
            //     if ( !err ) {
            //         console.log(JSON.stringify(data));
            //     }
            // });
        });
    }
*/

    render() {
        return (
            <Router>
                <div>
                    <Route exact path="/" component={HomepageComponent}/>
                    <Route path="/login" component={LoginPageComponent}/>
                    <Route path="/registration" component={RegistrationPageComponent}/>
                </div>
            </Router>
        );
    }
}

render(
    <ExamSlammrlApp/>,
    document.getElementById('app')
);
