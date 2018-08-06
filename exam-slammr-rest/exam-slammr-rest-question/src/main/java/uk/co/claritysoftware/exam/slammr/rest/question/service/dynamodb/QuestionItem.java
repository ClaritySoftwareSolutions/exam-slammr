package uk.co.claritysoftware.exam.slammr.rest.question.service.dynamodb;

import java.time.ZonedDateTime;
import java.util.List;

import com.amazonaws.annotation.Immutable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uk.co.claritysoftware.exam.slammr.rest.question.service.dynamodb.convertor.ZonedDateTimeConverter;

/**
 * Encapsulates the data for a Question dynamodb item
 * <p>
 * The AWS libraries use a jackson configuration which cannot be (easily) configured to allow for serialization and deserialization
 * of immutable classes. Therefore, this class is annotated with {@code @Data} to give it a default noargs constructor and getters
 * and setters; and {@code @Immutable} to indicate that instances of this class should be treated as immutable.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = "exam-slammr-questions")
@Immutable
public final class QuestionItem {

    @DynamoDBHashKey
    private String id;

    private String questionText;

    private List<AnswerDocument> answers;

    private List<String> tags;

    private List<String> certifications;

    private List<FurtherReadingDocument> furtherReadings;

    private String createdBy;

    @DynamoDBTypeConverted(converter = ZonedDateTimeConverter.class)
    private ZonedDateTime createdDateTime;
}