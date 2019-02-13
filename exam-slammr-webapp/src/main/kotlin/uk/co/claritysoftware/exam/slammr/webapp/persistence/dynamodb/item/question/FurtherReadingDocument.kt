package uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.item.question

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument
import java.io.Serializable

/**
 * Encapsulates the data for a further reading reference to a {@link QuestionItem} dynamodb item
 */
@DynamoDBDocument
data class FurtherReadingDocument(val description: String, val referenceLocation: String) : Serializable