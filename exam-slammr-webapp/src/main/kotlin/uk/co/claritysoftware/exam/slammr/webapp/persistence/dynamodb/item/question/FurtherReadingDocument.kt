package uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.item.question

import com.amazonaws.annotation.Immutable
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument
import java.io.Serializable

/**
 * Encapsulates the data for a further reading reference to a {@link QuestionItem} dynamodb item
 */
@DynamoDBDocument
@Immutable
data class FurtherReadingDocument(var description: String, var referenceLocation: String) : Serializable