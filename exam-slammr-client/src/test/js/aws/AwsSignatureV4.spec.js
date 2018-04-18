import sinon from 'sinon';
import {generateSigV4Headers} from "../../../main/js/aws/AwsSignatureV4.js"
import aws4 from 'aws4';

describe('Unit test class for AwsSignatureV4.js', () => {

    it("should generateSigV4Headers", () => {
        // Given
        let options = {
            'service': 'a-service',
            'region': 'a-region',
            'host': 'some.host.com',
            'path': '/some/path',
            'accessKey': 'an-access-key',
            'secretKey': 'a-secret-key',
            'sessionToken': 'a-session-token'
        };

        sinon.stub(aws4, 'sign').callsFake((opts, securityTokens) => {
            opts.headers = {
                'Authorization': 'the computed authorisation',
                'X-Amz-Date': 'a datetime stamp'
            }
        });

        let expectedHeaders = {
            'x-amz-security-token': 'a-session-token',
            'authorization': 'the computed authorisation',
            'X-Amz-Date': 'a datetime stamp'
        };

        // When
        let headers = generateSigV4Headers(options);

        // Then
        expect(headers).toEqual(expectedHeaders);
    })
});