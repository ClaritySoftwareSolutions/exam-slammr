package uk.co.claritysoftware.exam.slammr.user.rest.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.springframework.security.core.AuthenticatedPrincipal;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Encapsulates the data for a User Profile
 */
@Data
@NoArgsConstructor
@Builder
@DynamoDBTable(tableName = "exam-slammr-users")
public class UserProfile {

    @DynamoDBHashKey(attributeName = "webFederatedUserId")
    private String id;

    private String firstname;

    private String surname;

    private String nickname;

    private String email;

    private String profilePictureUrl;

    private ZonedDateTime registrationDateTime;

    private ZonedDateTime lastLogonDateTime;

    @JsonCreator
    private UserProfile(@JsonProperty("id") String id,
                        @JsonProperty("firstname") String firstname,
                        @JsonProperty("surname") String surname,
                        @JsonProperty("nickname") String nickname,
                        @JsonProperty("email") String email,
                        @JsonProperty("profilePictureUrl") String profilePictureUrl,
                        @JsonProperty("registrationDateTime") ZonedDateTime registrationDateTime,
                        @JsonProperty("lastLogonDateTime") ZonedDateTime lastLogonDateTime) {
        this.id = id;
        this.firstname = firstname;
        this.surname = surname;
        this.nickname = nickname;
        this.email = email;
        this.profilePictureUrl = profilePictureUrl;
        this.registrationDateTime = registrationDateTime;
        this.lastLogonDateTime = lastLogonDateTime;
    }
}
