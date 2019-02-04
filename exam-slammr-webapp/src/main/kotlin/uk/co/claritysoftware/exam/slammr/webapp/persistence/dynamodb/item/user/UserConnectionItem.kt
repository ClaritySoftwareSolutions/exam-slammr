package uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.item.user

import com.amazonaws.annotation.Immutable
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted
import uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.convertor.ZonedDateTimeConverter
import java.io.Serializable
import java.time.ZonedDateTime

/**
 * Encapsulates the data for a Social User Connection dynamodb item
 * <p>
 * The AWS libraries use a jackson configuration which cannot be (easily) configured to allow for serialization and deserialization
 * of immutable classes. Therefore, this class is annotated with {@code @Immutable} to indicate that instances of this class should
 * be treated as immutable.
 */
@DynamoDBTable(tableName = "exam-slammr-user-connections")
@Immutable
data class UserConnectionItem(@DynamoDBHashKey
							  val userId: String,

							  val providerId: String,

							  var providerUserId: String? = null,

							  val rank: Int,

							  var displayName: String? = null,

							  var profileUrl: String? = null,

							  var imageUrl: String? = null,

							  val accessToken: String,

							  var secret: String? = null,

							  var refreshToken: String? = null,

							  @DynamoDBTypeConverted(converter = ZonedDateTimeConverter::class)
							  var expireTime: ZonedDateTime? = null
) : Serializable