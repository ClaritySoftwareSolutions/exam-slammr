package uk.co.claritysoftware.exam.slammr.user.testsupport.service.dynamodb;

import uk.co.claritysoftware.exam.slammr.user.rest.model.UserProfile;
import uk.co.claritysoftware.exam.slammr.user.service.dynamodb.UserProfileItem;

import java.time.ZonedDateTime;

import static java.time.format.DateTimeFormatter.ISO_ZONED_DATE_TIME;

/**
 * Test data factory for {@link UserProfile} instances
 */
public class UserProfileItemTestDataFactory {

    /**
     * @return a User Profile Item Builder containing a valid user profile item, ready to have it's build method called
     */
    public static UserProfileItem.UserProfileItemBuilder mrBurnsUserProfileItem() {
        return UserProfileItem.builder()
                .webFederatedUserId("12345")
                .firstname("Montgomery")
                .surname("Burns")
                .nickname("Mr Burns")
                .email("monty.burns@springfield.com")
                .profilePictureUrl("http://profile.pics/monty.burns")
                .registrationDateTime(ZonedDateTime.parse("2018-01-20T12:20:30.123Z", ISO_ZONED_DATE_TIME))
                .lastLogonDateTime(ZonedDateTime.parse("2018-05-09T08:12:43.456Z", ISO_ZONED_DATE_TIME));
    }

    /**
     * @return a User Profile Item Builder containing a valid user profile item, ready to have it's build method called
     */
    public static UserProfileItem.UserProfileItemBuilder smithersUserProfileItem() {
        return UserProfileItem.builder()
                .webFederatedUserId("67890")
                .firstname("Waylon")
                .surname("Smithers")
                .nickname("Smithers")
                .email("waylon.smithers@springfield.com")
                .profilePictureUrl("http://profile.pics/waylon.smithers")
                .registrationDateTime(ZonedDateTime.parse("2018-01-20T12:20:30.123Z", ISO_ZONED_DATE_TIME))
                .lastLogonDateTime(ZonedDateTime.parse("2018-05-09T08:12:43.456Z", ISO_ZONED_DATE_TIME));
    }

}