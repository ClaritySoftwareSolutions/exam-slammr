package uk.co.claritysoftware.exam.slammr.rest.user.service.dynamodb.convertor;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;

public class ZonedDateTimeConverter implements DynamoDBTypeConverter<String, ZonedDateTime> {

    @Override
    public String convert(ZonedDateTime zonedDateTime) {
        return zonedDateTime.format(DateTimeFormatter.ISO_ZONED_DATE_TIME);
    }

    @Override
    public ZonedDateTime unconvert(String stringValue) {
        return ZonedDateTime.parse(stringValue, DateTimeFormatter.ISO_ZONED_DATE_TIME);
    }
}
