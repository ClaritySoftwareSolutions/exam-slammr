import Promise from 'promise';

/*
Function to load the Facebook SDK configured with the specified Application ID and return a promise
 */
export function loadFaceBookApi(appId) {

    (function loadFacebookSdkAsynchronously() {
        const ID = "facebook-jssdk";
        if (document.getElementById(ID)) return;
        const fjs = document.getElementsByTagName("script")[0];
        const js = document.createElement("script");
        js.id = "facebook-jssdk";
        js.src = "//connect.facebook.net/en_US/sdk.js";
        fjs.parentNode.insertBefore(js, fjs);
    }());

    return new Promise((resolve, reject) => {
        window.fbAsyncInit = function () {
            FB.init({
                appId: appId,
                cookie: true,   // enable cookies to allow the server to access he session
                xfbml: true,    // parse social plugins on this page
                version: 'v2.5' // use version 2.5
            });
            resolve();
        };
    });
}

/*
Function to check the Facebook login status and return a promise
 */
export function facebookLoginStatus() {
    return new Promise((resolve, reject) => {
        FB.getLoginStatus((response) => {
            resolve(response)
        })
    });
}

/*
Function to call Facebook Me api and return a promise
 */
export function facebookMe() {
    return new Promise((resolve, reject) => {
        FB.api('/me', (response) => {
            resolve(response);
        })
    });
}

/*
Function to call Facebook login and return a promise
 */
export function facebookLogin() {
    return new Promise((resolve, reject) => {
        FB.login((response) => {
            resolve(response);
        }, {scope: 'public_profile'});
    });
}

/*
Function to call Facebook logout and return a promise
 */
export function facebookLogout() {
    return new Promise((resolve, reject) => {
        FB.logout((response) => {
            resolve(response);
        });
    });
}
