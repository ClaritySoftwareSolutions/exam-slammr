package uk.co.claritysoftware.exam.slammr.webapp.web.model;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * Unit test class for {@link CreateQuestion}
 */
public class CreateQuestionTest {

	@Test
	public void shouldGenerateEmptyCreateQuestion() {
		// Given
		CreateQuestion expectedCreateQuestion = CreateQuestion.builder()
				.action(null)
				.summary(null)
				.question(null)
				.tags(singletonList(""))
				.certifications(singletonList(""))
				.build();

		// When
		CreateQuestion createQuestion = CreateQuestion.generateEmptyCreateQuestion();

		// Then
		assertThat(createQuestion).isEqualTo(expectedCreateQuestion);
	}

	@Test
	public void shouldHonourEqualsHashcodeContract() {
		EqualsVerifier.forClass(CreateQuestion.class)
				.suppress(Warning.NONFINAL_FIELDS)
				.verify();
	}

}