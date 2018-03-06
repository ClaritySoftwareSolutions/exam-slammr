package uk.co.claritysoftware.exam.slammr.lambda.question.dto;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

import java.io.IOException;
import org.junit.Test;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Unit test class for {@link CreateQuestionRequest}
 */
public class CreateQuestionRequestTest {

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	@Test
	public void shouldConstructGivenAnswersWithOneCorrectAnswer() throws IOException {
		// Given
		String json = "{"
				+ "\"questionText\":\"What is 1 + 1?\","
				+ "\"answers\":["
				+ "  {\"text\":\"3\", \"isCorrect\":\"false\"},"
				+ "  {\"text\":\"2\", \"isCorrect\":\"true\"}"
				+ "],"
				+ "\"tags\":[],"
				+ "\"certifications\":[],"
				+ "\"furtherReadings\":[]"
				+ "}";

		// When
		CreateQuestionRequest createQuestionRequest = OBJECT_MAPPER.readValue(json, CreateQuestionRequest.class);

		// Then
		assertThat(createQuestionRequest, is(not(nullValue())));
	}

	@Test
	public void shouldFailToConstructGivenAnswersWithZeroCorrectAnswers() throws IOException {
		// Given
		String json = "{"
				+ "\"questionText\":\"What is 1 + 1?\","
				+ "\"answers\":["
				+ "  {\"text\":\"1\", \"isCorrect\":\"false\"},"
				+ "  {\"text\":\"3\", \"isCorrect\":\"false\"}"
				+ "],"
				+ "\"tags\":[],"
				+ "\"certifications\":[],"
				+ "\"furtherReadings\":[]"
				+ "}";

		// When
		try {
			OBJECT_MAPPER.readValue(json, CreateQuestionRequest.class);
			fail("Was expecting an IllegalStateException");
		}
		// Then
		catch (JsonMappingException e) {
			assertThat(e.getCause().getMessage(), is("Question should have exactly 1 answer marked as the correct answer, but it has 0"));
		}
	}

	@Test
	public void shouldFailToConstructGivenAnswersWithTwoCorrectAnswers() throws IOException {
		// Given
		String json = "{"
				+ "\"questionText\":\"What is 1 + 1?\","
				+ "\"answers\":["
				+ "  {\"text\":\"1\", \"isCorrect\":\"false\"},"
				+ "  {\"text\":\"2\", \"isCorrect\":\"true\"},"
				+ "  {\"text\":\"two\", \"isCorrect\":\"true\"},"
				+ "  {\"text\":\"3\", \"isCorrect\":\"false\"}"
				+ "],"
				+ "\"tags\":[],"
				+ "\"certifications\":[],"
				+ "\"furtherReadings\":[]"
				+ "}";

		// When
		try {
			OBJECT_MAPPER.readValue(json, CreateQuestionRequest.class);
			fail("Was expecting an IllegalStateException");
		}
		// Then
		catch (JsonMappingException e) {
			assertThat(e.getCause().getMessage(), is("Question should have exactly 1 answer marked as the correct answer, but it has 2"));
		}
	}
}