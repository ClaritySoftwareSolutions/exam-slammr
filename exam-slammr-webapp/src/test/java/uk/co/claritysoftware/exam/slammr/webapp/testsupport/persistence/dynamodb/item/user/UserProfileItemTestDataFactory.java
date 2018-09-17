package uk.co.claritysoftware.exam.slammr.webapp.testsupport.persistence.dynamodb.item.user;

import uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.item.user.UserProfileItem;

import java.time.ZonedDateTime;

import static com.google.common.collect.Sets.newHashSet;
import static java.time.format.DateTimeFormatter.ISO_ZONED_DATE_TIME;

/**
 * Test data factory for {@link UserProfileItem} instances
 */
public class UserProfileItemTestDataFactory {

    /**
     * @return a User Profile Item Builder containing a valid user profile item, ready to have it's build method called
     */
    public static UserProfileItem.UserProfileItemBuilder mrBurnsUserProfileItem() {
        return UserProfileItem.builder()
                .id("12345")
                .compositeUserId("twitter:09876")
                .firstname("Montgomery")
                .surname("Burns")
                .nickname("Mr Burns")
                .email("monty.burns@springfield.com")
                .profilePictureUrl("http://profile.pics/monty.burns")
                .roles(newHashSet("USER"))
                .registrationDateTime(ZonedDateTime.parse("2018-01-20T12:20:30.123Z", ISO_ZONED_DATE_TIME))
                .lastLogonDateTime(ZonedDateTime.parse("2018-05-09T08:12:43.456Z", ISO_ZONED_DATE_TIME));
    }

    /**
     * @return a User Profile Item Builder containing a valid user profile item, ready to have it's build method called
     */
    public static UserProfileItem.UserProfileItemBuilder smithersUserProfileItem() {
        return UserProfileItem.builder()
                .id("67890")
                .compositeUserId("facebook:54321")
                .firstname("Waylon")
                .surname("Smithers")
                .nickname("Smithers")
                .email("waylon.smithers@springfield.com")
                .profilePictureUrl("http://profile.pics/waylon.smithers")
                .roles(newHashSet("USER"))
                .registrationDateTime(ZonedDateTime.parse("2018-01-20T12:20:30.123Z", ISO_ZONED_DATE_TIME))
                .lastLogonDateTime(ZonedDateTime.parse("2018-05-09T08:12:43.456Z", ISO_ZONED_DATE_TIME));
    }

}
