package uk.co.claritysoftware.exam.slammr.user.service.dynamodb;

import com.amazonaws.annotation.Immutable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

/**
 * Encapsulates the data for a User Profile dynamodb item
 * <p>
 * The AWS libraries use a jackson configuration which cannot be (easily) configured to allow for serialization and deserialization
 * of immutable classes. Therefore, this class is annotated with {@code @Data} to give it a default noargs constructor and getters
 * and setters; and {@code @Immutable} to indicate that instances of this class should be treated as immutable.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = "exam-slammr-users")
@Immutable
public final class UserProfileItem {

    @DynamoDBHashKey
    private String webFederatedUserId;

    private String firstname;

    private String surname;

    private String nickname;

    private String email;

    private String profilePictureUrl;

    private ZonedDateTime registrationDateTime;

    private ZonedDateTime lastLogonDateTime;

}
