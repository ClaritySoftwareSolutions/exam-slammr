package uk.co.claritysoftware.exam.slammr.webapp.service.model.question;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import uk.co.claritysoftware.exam.slammr.webapp.service.model.question.Question;

/**
 * Unit test class for {@link Question}
 */
public class QuestionTest {

	@Test
	public void shouldHonourEqualsHashcodeContract() {
		EqualsVerifier.forClass(Question.class)
				.verify();
	}
}