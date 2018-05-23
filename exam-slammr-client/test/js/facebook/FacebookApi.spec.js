import sinon from 'sinon';
import {facebookLogin} from '../../../src/js/facebook/FacebookApi.js';

describe("Unit test class for FacebookApi.js", () => {

    global.FB = {
        login: () => {}
    };
    let fbApiResponse;
    sinon.stub(FB, 'login').returns(fbApiResponse);

    it("should return a resolved promise given a successful FB response", () => {
        // Given
        fbApiResponse = {
            'status': 'connected'
        };

        // When
        let loginPromise = facebookLogin();

        // Then
        loginPromise.then(response => {
            expect(response).toBeEqualTo(fbApiResponse)
        })
    });

    it("should return a rejected promise given a failure FB response", () => {
        // Given
        fbApiResponse = {
            'status': ''
        };

        // When
        let loginPromise = facebookLogin();

        // Then
        loginPromise.catch(response => {
            expect(response).toBeEqualTo(fbApiResponse)
        })
    });
});