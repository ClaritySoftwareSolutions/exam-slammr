package uk.co.claritysoftware.exam.slammr.rest.user.testsupport.web.model;

import static java.time.format.DateTimeFormatter.ISO_ZONED_DATE_TIME;

import java.time.ZonedDateTime;
import uk.co.claritysoftware.exam.slammr.rest.user.web.model.UserProfile;

/**
 * Test data factory for {@link UserProfile} instances
 */
public class UserProfileTestDataFactory {

    /**
     * @return a User Profile Builder containing a valid user profile, ready to have it's build method called
     */
    public static UserProfile.UserProfileBuilder mrBurnsUserProfile() {
        return UserProfile.builder()
                .firstname("Montgomery")
                .surname("Burns")
                .nickname("Mr Burns")
                .email("monty.burns@springfield.com")
                .profilePictureUrl("http://profile.pics/monty.burns")
                .registrationDateTime(ZonedDateTime.parse("2018-01-20T12:20:30.123Z", ISO_ZONED_DATE_TIME))
                .lastLogonDateTime(ZonedDateTime.parse("2018-05-09T08:12:43.456Z", ISO_ZONED_DATE_TIME));
    }
}
