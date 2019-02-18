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
 */
@DynamoDBTable(tableName = "exam-slammr-user-connections")
@Immutable
data class UserConnectionItem(@field:DynamoDBHashKey
							  var userId: String,

							  var providerId: String,

							  var providerUserId: String? = null,

							  var rank: Int,

							  var displayName: String? = null,

							  var profileUrl: String? = null,

							  var imageUrl: String? = null,

							  var accessToken: String,

							  var secret: String? = null,

							  var refreshToken: String? = null,

							  @field:DynamoDBTypeConverted(converter = ZonedDateTimeConverter::class)
							  var expireTime: ZonedDateTime? = null
) : Serializable