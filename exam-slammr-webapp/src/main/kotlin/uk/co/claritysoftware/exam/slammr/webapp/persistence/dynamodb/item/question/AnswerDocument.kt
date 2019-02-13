package uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.item.question

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument
import java.io.Serializable

/**
 * Encapsulates the data for a potential Answer to a {@link QuestionItem} dynamodb item
 */
@DynamoDBDocument
data class AnswerDocument(val text: String, val correct: Boolean) : Serializable