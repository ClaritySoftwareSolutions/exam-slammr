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
							  var userId: String?,

							  var providerId: String?,

							  var providerUserId: String?,

							  var rank: Int?,

							  var displayName: String?,

							  var profileUrl: String?,

							  var imageUrl: String?,

							  var accessToken: String?,

							  var secret: String?,

							  var refreshToken: String?,

							  @field:DynamoDBTypeConverted(converter = ZonedDateTimeConverter::class)
							  var expireTime: ZonedDateTime?
) : Serializable {

	constructor() : this(userId = null,
			providerId = null,
			providerUserId = null,
			rank = null,
			displayName = null,
			profileUrl = null,
			imageUrl = null,
			accessToken = null,
			secret = null,
			refreshToken = null,
			expireTime = null)
}