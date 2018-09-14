package uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.item.question;

import com.amazonaws.annotation.Immutable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Encapsulates the data for a further reading reference to a {@link QuestionItem} dynamodb item
 * <p>
 * The AWS libraries use a jackson configuration which cannot be (easily) configured to allow for serialization and deserialization
 * of immutable classes. Therefore, this class is annotated with {@code @Data} to give it a default noargs constructor and getters
 * and setters; and {@code @Immutable} to indicate that instances of this class should be treated as immutable.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBDocument
@Immutable
public final class FurtherReadingDocument {

    private String description;

    private String referenceLocation;
}
