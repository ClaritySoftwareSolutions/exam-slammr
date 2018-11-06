package uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.convertor

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter

class ZonedDateTimeConverter : DynamoDBTypeConverter<String, ZonedDateTime> {

    override fun convert(zonedDateTime: ZonedDateTime): String {
        return zonedDateTime.format(DateTimeFormatter.ISO_ZONED_DATE_TIME)
    }

    override fun unconvert(stringValue: String): ZonedDateTime {
        return ZonedDateTime.parse(stringValue, DateTimeFormatter.ISO_ZONED_DATE_TIME)
    }
}
