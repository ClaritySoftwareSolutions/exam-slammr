import aws4 from 'aws4';

export function interceptor(request) {

    const url = request.url;
    const urlWithoutScheme = url.substr(url.indexOf("//")+2);

    const options = {
        host: urlWithoutScheme.split("/")[0],
        path: urlWithoutScheme.substr(urlWithoutScheme.indexOf("/")),
        method: request.method.toUpperCase(),
        body: JSON.stringify(request.data),
        headers: {
            "Content-Type": request.headers["Content-Type"]}
    }

    const awsAuth = request.awsAuth;
    const signature = aws4.sign(options, awsAuth);
    delete signature.headers["Host"];
    delete signature.headers["Content-Length"];
    request.headers = signature.headers;
    return request;
}
