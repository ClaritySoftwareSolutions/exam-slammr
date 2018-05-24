/**
 * Entry point for all User lambdas
 */

const registerNewUserHandler = require('./RegisterNewUserRequestHandler');
const getUserProfileHandler = require('./GetUserProfileRequestHandler');

exports.registerNewUser = ((event, context, callback) => {
    registerNewUserHandler.handle(event, context, callback);
});

exports.getUserProfile = ((event, context, callback) => {
    getUserProfileHandler.handle(event, context, callback);
});
