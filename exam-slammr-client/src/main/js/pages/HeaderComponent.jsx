/*
React component for the page header
 */

import React, { Component } from 'react';
import UserConcern from "../user/UserConcern.jsx";

class HeaderComponent extends Component {

    constructor (props) {
        super(props);
    }

    render() {
        return (
            <header>
                <h1><a href="/#/">Exam Slammr</a></h1>
                <UserConcern/>
            </header>
        );
    }
}

export default HeaderComponent;