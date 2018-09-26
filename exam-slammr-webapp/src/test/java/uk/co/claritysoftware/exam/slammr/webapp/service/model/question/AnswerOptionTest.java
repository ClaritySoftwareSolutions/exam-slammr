package uk.co.claritysoftware.exam.slammr.webapp.service.model.question;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import uk.co.claritysoftware.exam.slammr.webapp.service.model.question.AnswerOption;

/**
 * Unit test class for {@link AnswerOption}
 */
public class AnswerOptionTest {

	@Test
	public void shouldHonourEqualsHashcodeContract() {
		EqualsVerifier.forClass(AnswerOption.class)
				.verify();
	}
}