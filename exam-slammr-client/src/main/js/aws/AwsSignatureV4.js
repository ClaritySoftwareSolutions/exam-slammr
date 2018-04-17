import aws4 from 'aws4';

/*
Generates an AWS Signature V4 and returns a map of headers suitable for making a HTTP call to an aws service with
 */
export function generateSigV4Headers(options) {
    let opts = {
        'service': options.service,
        'region': options.region,
        'host': options.host,
        'path': options.path
    };
    aws4.sign(opts, {accessKeyId: options.accessKey, secretAccessKey: options.secretKey, sessionToken: options.sessionToken});

    return {
        'x-amz-security-token': options.sessionToken,
        'authorization': opts.headers.Authorization,
        'X-Amz-Date': opts.headers['X-Amz-Date']
    }

}