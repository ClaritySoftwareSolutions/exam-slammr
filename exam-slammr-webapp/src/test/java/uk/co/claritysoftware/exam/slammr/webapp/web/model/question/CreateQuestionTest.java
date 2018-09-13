package uk.co.claritysoftware.exam.slammr.webapp.web.model.question;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import uk.co.claritysoftware.exam.slammr.webapp.web.model.question.CreateQuestion;

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
				.answerOptions(asList(
						AnswerOption.builder().build(),
						AnswerOption.builder().build()
				))
				.tags(singletonList(""))
				.certifications(singletonList(""))
				.furtherReadings(singletonList(FurtherReading.builder().build()))
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