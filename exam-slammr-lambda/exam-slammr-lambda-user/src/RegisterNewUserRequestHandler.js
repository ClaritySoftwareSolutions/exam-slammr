/**
 * Request handler to register a new user
 */
const UserProfileRepository = require('./UserProfileRepository');
const moment = require('moment');

const isEmptyObject = ((obj) => {
    return !obj || (Object.keys(obj).length === 0 && obj.constructor === Object);
});

exports.handle = ((event, context, callback) => {

    if (!context.identity || !context.identity.cognitoIdentityId) {
        callback(null, {
            statusCode: 401,
            body: 'Must be called with a Cognito Identity'
        });
        return;
    }

    let identityId = context.identity.cognitoIdentityId;
    let requestBody = JSON.parse(event.body);

    UserProfileRepository.getUserProfile(identityId)
        .then((userProfile) => {

            if (isEmptyObject(userProfile)) {
                console.debug('User with identity %s does not exist. Attempting to create', identityId);
                let newUser = {
                    'webFederatedUserId': {'S': identityId},
                    'name': {'S': requestBody.userProfile.name},
                    'email': {'S': requestBody.userProfile.email},
                    'identityProvider': {'S': requestBody.socialIdentityProvider},
                    'profilePictureUrl': {'S': requestBody.userProfile.profilePicture.url},
                    'createdDate': {'S': moment().format()}
                };

                UserProfileRepository.createUserProfile(newUser)
                    .then((userProfile) => {
                        console.debug('UserProfile created ', userProfile);
                        callback(null, {
                            statusCode: 201,
                            body: JSON.stringify(userProfile)
                        });
                    })
                    .catch((error) => {
                        console.error('An error occurred whilst persisting the new UserProfile', error);
                        callback(null, {
                            statusCode: error.statusCode,
                            body: error.message
                        });
                    })
            } else {
                console.debug('User with identity %s has already been registered. Returning the UserProfile', identityId);
                callback(null, {
                    statusCode: 200,
                    body: JSON.stringify(userProfile)
                });
            }

        })
        .catch((error) => {
            console.error('An error occurred whilst attempting to retrieve the UserProfile', error);
            callback(null, {
                statusCode: error.statusCode,
                body: error.message
            });
        });
});