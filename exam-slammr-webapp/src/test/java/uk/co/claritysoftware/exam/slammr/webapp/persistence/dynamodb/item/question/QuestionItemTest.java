package uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.item.question;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

/**
 * Unit test class for {@link QuestionItem}
 */
public class QuestionItemTest {

	@Test
	public void shouldHonourEqualsHashcodeContract() {
		EqualsVerifier.forClass(QuestionItem.class)
				.verify();
	}

}