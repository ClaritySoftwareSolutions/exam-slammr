package uk.co.claritysoftware.exam.slammr.webapp.testsupport.web.model.user

import uk.co.claritysoftware.exam.slammr.webapp.web.model.user.SocialUserSignUp

/**
 * Test data factory for [SocialUserSignUp] instances
 */

/**
 * @return a SocialUserSignUp Builder containing a valid user signup, ready to have it's build method called
 */
fun bartSimposonsUserSignup(): SocialUserSignUp.SocialUserSignUpBuilder {
    return SocialUserSignUp.builder()
            .firstName("Bart")
            .lastName("Simpson")
            .nickName("el Barto")
            .email("bart@simpsons.com")
}