package uk.co.claritysoftware.exam.slammr.user.testsupport.rest.model;

import uk.co.claritysoftware.exam.slammr.user.rest.model.UserRegistrationRequest;

public class UserRegistrationRequestTestDataFactory {

    /**
     * @return a User Registration Request Builder containing a valid user registration request ready to have it's build method called
     */
    public static UserRegistrationRequest.UserRegistrationRequestBuilder smithersUserRegistrationRequest() {
        return UserRegistrationRequest.builder()
                .firstname("Waylon")
                .surname("Smithers")
                .nickname("Smithers")
                .email("waylon.smithers@springfield.com")
                .profilePictureUrl("http://profile.pics/waylon.smithers");
    }
}
