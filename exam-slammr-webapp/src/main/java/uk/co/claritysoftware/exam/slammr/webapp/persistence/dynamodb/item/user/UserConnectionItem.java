package uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.item.user;

import java.time.ZonedDateTime;
import com.amazonaws.annotation.Immutable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;

import uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.convertor.ZonedDateTimeConverter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Encapsulates the data for a Social User Connection dynamodb item
 * <p>
 * The AWS libraries use a jackson configuration which cannot be (easily) configured to allow for serialization and deserialization
 * of immutable classes. Therefore, this class is annotated with {@code @Data} to give it a default noargs constructor and getters
 * and setters; and {@code @Immutable} to indicate that instances of this class should be treated as immutable.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = "exam-slammr-user-connections")
@Immutable
public final class UserConnectionItem {

	@NonNull
	@DynamoDBHashKey
	private String userId;

	@NonNull
	private String providerId;

	private String providerUserId;

	@NonNull
	private Integer rank;

	private String displayName;

	private String profileUrl;

	private String imageUrl;

	@NonNull
	private String accessToken;

	private String secret;

	private String refreshToken;

	@DynamoDBTypeConverted(converter = ZonedDateTimeConverter.class)
	private ZonedDateTime expireTime;

	public UserConnectionItem.UserConnectionItemBuilder copy() {
		return UserConnectionItem.builder()
				.userId(userId)
				.providerId(providerId)
				.providerUserId(providerUserId)
				.rank(rank)
				.displayName(displayName)
				.profileUrl(profileUrl)
				.imageUrl(imageUrl)
				.accessToken(accessToken)
				.secret(secret)
				.refreshToken(refreshToken)
				.expireTime(expireTime);
	}
}
