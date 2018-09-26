package uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.item.question;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

/**
 * Unit test class for {@link AnswerDocument}
 */
public class AnswerDocumentTest {

	@Test
	public void shouldHonourEqualsHashcodeContract() {
		EqualsVerifier.forClass(AnswerDocument.class)
				.verify();
	}

}