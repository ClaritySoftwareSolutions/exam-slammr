package uk.co.claritysoftware.exam.slammr.user.rest.testsupport.model;

import uk.co.claritysoftware.exam.slammr.user.rest.model.UserProfile;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Test data factory for {@link UserProfile} instances
 */
public class UserProfileTestDataFactory {

    private static final UserProfile.UserProfileBuilder USER_PROFILE = UserProfile.builder()
            .id("12345")
            .firstname("Montgomery")
            .surname("Burns")
            .nickname("Mr Burns")
            .email("monty.burns@springfield.com")
            .profilePictureUrl("http://profile.pics/monty.burns")
            .registrationDateTime(ZonedDateTime.parse("2018-01-20T12:20:30.123Z", DateTimeFormatter.ISO_DATE_TIME))
            .lastLogonDateTime(ZonedDateTime.parse("2018-05-09T08:12:43.456Z", DateTimeFormatter.ISO_DATE_TIME));

    /**
     * @return a User Profile Builder containing a valid user profile, ready to have it's build method called
     */
    public static UserProfile.UserProfileBuilder aValidUserProfile() {
        return USER_PROFILE;
    }
}