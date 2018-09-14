package uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.item.question;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

/**
 * Unit test class for {@link FurtherReadingDocument}
 */
public class FurtherReadingDocumentTest {

	@Test
	public void shouldHonourEqualsHashcodeContract() {
		EqualsVerifier.forClass(FurtherReadingDocument.class)
				.verify();
	}
}