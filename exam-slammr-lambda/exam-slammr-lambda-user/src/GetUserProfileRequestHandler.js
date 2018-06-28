/**
 * Request handler to get a user profile
 */
const UserProfileRepository = require('./UserProfileRepository');

exports.handle = ((event, context, callback) => {

    if (!context.identity || !context.identity.cognitoIdentityId) {
        callback(null, {
            statusCode: 401,
            body: 'Must be called with a Cognito Identity'
        });
        return;
    }

    let identityId = context.identity.cognitoIdentityId;

    UserProfileRepository.getUserProfile(identityId)
        .then((userProfile) => {
            callback(null, {
                statusCode: 200,
                body: JSON.stringify(userProfile)
            })
        })
        .catch((error) => {
            console.error(error);
            callback(null, {
                statusCode: error.statusCode,
                body: error.message
            });
        });
});