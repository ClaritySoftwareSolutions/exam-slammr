/**
 * Entry point for all User lambdas
 */

const registerNewUserHandler = require('./RegisterNewUserRequestHandler');

exports.registerNewUser = ((event, context, callback) => {
    registerNewUserHandler.handle(event, context, callback);
});
