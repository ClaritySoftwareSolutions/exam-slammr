import {interceptor} from "../../../src/js/aws/AwsSignatureV4AxiosInterceptor.js"

describe('Unit test class for AwsSignatureV4AxiosInterceptor.js', () => {

    it('should intercept http request and add AWS Signature V4 headers', () => {
        // Given
        let httpRequest = {
            'method': 'post',
            'url': 'http://localhost/some/path',
            'data': {
                'key': 'value',
                'otherKey': 'other value'
            },
            'headers': {
                'Content-Type': 'application/json'
            },
            'awsAuth': {
                'secretAccessKey': 'secretKey',
                'accessKeyId': 'accessKey',
                'sessionToken': 'sessionToken'
            }
        };

        // When
        interceptor(httpRequest);

        // Then
        expect(httpRequest.headers).toHaveProperty('Content-Type', 'application/json')
        expect(httpRequest.headers).toHaveProperty('Authorization')
        expect(httpRequest.headers).toHaveProperty('X-Amz-Date')
        expect(httpRequest.headers).toHaveProperty('X-Amz-Security-Token', 'sessionToken')
        expect(httpRequest).not.toHaveProperty('awsAuth')
    })
});