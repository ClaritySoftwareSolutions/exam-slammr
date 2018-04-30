import sinon from 'sinon';
import {facebookLogin} from '../../../main/js/facebook/FacebookApi.js';

describe("Unit test class for FacebookApi.js", () => {

    let FB = {
        login: () => {}
    };

    it("should perform facebookLogin given a success response", () => {
        // Given
        let response = {
            'status': 'connected'
        };
        sinon.stub(FB, 'login').returns(response);

        // When
        let loginPromise = facebookLogin();

        // Then
        console.log('return', loginPromise)
    });
});