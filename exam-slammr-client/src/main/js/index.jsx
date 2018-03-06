import React from 'react';
import {render} from 'react-dom';

import {loadFaceBookApi, facebookLoginStatus} from "./facebook/FacebookApi.js";
import LoginComponent from "./facebook/LoginComponent.jsx";

import 'bootstrap/dist/css/bootstrap.min.css'
import '../scss/main.scss';

class ExamSlammrlApp extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            fbLoginStatus: {
                status: undefined,
                username: undefined,
                userId: undefined
            }
        };
    }

    componentDidMount() {
        const APP_ID = '302525613607160';
        loadFaceBookApi(APP_ID).then(() => {
            facebookLoginStatus().then((fbLoginStatus) => {
                this.setState({fbLoginStatus})
            })
        });
    }

    render() {
        return (
            <div>
                <div class="fb-login-button" data-max-rows="1" data-size="medium" data-button-type="login_with" data-show-faces="false" data-auto-logout-link="true" data-use-continue-as="false"></div>
            </div>
        );
    }
}

render(<ExamSlammrlApp/>, document.getElementById('app'));
