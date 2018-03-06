import React from 'react';
import {render} from 'react-dom';

import LoginComponent from "./facebook/LoginComponent.jsx";

import 'bootstrap/dist/css/bootstrap.min.css'
import '../scss/main.scss';

class ExamSlammrlApp extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
        };
    }

    render() {
        return (
            <div>
                <LoginComponent/>
            </div>
        );
    }
}

render(<ExamSlammrlApp/>, document.getElementById('app'));
