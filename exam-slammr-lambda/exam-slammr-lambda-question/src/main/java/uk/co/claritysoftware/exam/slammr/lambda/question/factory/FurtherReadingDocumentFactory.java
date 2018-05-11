package uk.co.claritysoftware.exam.slammr.lambda.question.factory;

import uk.co.claritysoftware.exam.slammr.lambda.question.dto.FurtherReading;
import uk.co.claritysoftware.exam.slammr.lambda.question.dynamodb.items.FurtherReadingDocument;

/**
 * @author Nathan Russell
 */
public class FurtherReadingDocumentFactory {

	public static FurtherReadingDocument from(FurtherReading furtherReading) {
		return FurtherReadingDocument.builder()
				.description(furtherReading.getDescription())
				.referenceLocation(furtherReading.getReferenceLocation())
				.build();
	}
}
