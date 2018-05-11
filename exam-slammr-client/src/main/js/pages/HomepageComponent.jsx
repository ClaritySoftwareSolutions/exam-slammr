/*
React component for the initial Home page
 */

import React, { Component } from 'react';
import HeaderComponent from "./HeaderComponent.jsx";

class HomepageComponent extends Component {

    constructor(props) {
        super(props);
    }

    render() {
        return(
            <div>
                <HeaderComponent/>
                <h2>Welcome!</h2>
            </div>
        );
    }
}

export default HomepageComponent;