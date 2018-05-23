import aws4 from 'aws4';

/**
 * An Axios request interceptor that calculates and adds the AWS Signature V4 headers
 */
export function interceptor(request) {
console.log("Building AWS Sig from ", JSON.stringify(request))
    const url = request.url;
    const urlWithoutScheme = url.substr(url.indexOf("//")+2);

    const options = {
        host: urlWithoutScheme.split("/")[0],
        path: urlWithoutScheme.substr(urlWithoutScheme.indexOf("/")),
        method: request.method.toUpperCase(),
        body: JSON.stringify(request.data),
        headers: {
            "Content-Type": request.headers["Content-Type"]
        }
    }

    const awsAuth = request.awsAuth;
    const signature = aws4.sign(options, awsAuth);

    delete request.awsAuth;
    delete signature.headers["Host"];
    delete signature.headers["Content-Length"];

    request.headers = signature.headers;

console.log("Built request headers ", JSON.stringify(request.headers))

    return request;
}
