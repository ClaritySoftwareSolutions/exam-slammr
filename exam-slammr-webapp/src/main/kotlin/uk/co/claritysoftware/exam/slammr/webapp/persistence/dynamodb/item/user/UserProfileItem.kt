package uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.item.user

import com.amazonaws.annotation.Immutable
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted
import uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.convertor.ZonedDateTimeConverter
import java.io.Serializable
import java.time.ZonedDateTime

/**
 * Encapsulates the data for a User Profile dynamodb item
 */
@DynamoDBTable(tableName = "exam-slammr-users")
@Immutable
data class UserProfileItem(@field:DynamoDBHashKey
						   @field:DynamoDBAutoGeneratedKey
						   var id: String?,

						   var compositeUserId: String,

						   var firstname: String? = null,

						   var surname: String? = null,

						   var nickname: String? = null,

						   var email: String? = null,

						   var profilePictureUrl: String? = null,

						   var roles: List<String>,

						   @field:DynamoDBTypeConverted(converter = ZonedDateTimeConverter::class)
						   var registrationDateTime: ZonedDateTime? = null,

						   @field:DynamoDBTypeConverted(converter = ZonedDateTimeConverter::class)
						   var lastLogonDateTime: ZonedDateTime? = null

) : Serializable