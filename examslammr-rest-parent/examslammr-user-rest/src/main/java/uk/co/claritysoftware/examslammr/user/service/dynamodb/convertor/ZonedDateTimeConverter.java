package uk.co.claritysoftware.examslammr.user.service.dynamodb.convertor;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

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
