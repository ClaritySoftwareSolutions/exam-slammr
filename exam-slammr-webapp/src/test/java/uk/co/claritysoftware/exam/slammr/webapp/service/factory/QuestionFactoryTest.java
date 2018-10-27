package uk.co.claritysoftware.exam.slammr.webapp.service.factory;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.claritysoftware.exam.slammr.webapp.testsupport.persistence.dynamodb.item.question.QuestionItemTestDataFactoryKt.aSimpleQuestionItemAboutTriangles;
import static uk.co.claritysoftware.exam.slammr.webapp.testsupport.service.model.question.QuestionTestDataFactoryKt.aSimpleQuestionAboutTriangles;

import org.junit.Test;
import uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.item.question.QuestionItem;
import uk.co.claritysoftware.exam.slammr.webapp.service.model.question.Question;

/**
 * Unit test class for {@link QuestionFactory}
 */
public class QuestionFactoryTest {

    @Test
    public void shouldDeriveQuestionValueOfGivenQuestionItem() {
        // Given
        QuestionItem questionItem = aSimpleQuestionItemAboutTriangles().build();
        Question expectedQuestion = aSimpleQuestionAboutTriangles().build();

        // When
        Question question = QuestionFactory.valueOf(questionItem);

        // Then
        assertThat(question)
                .isEqualTo(expectedQuestion);
    }

    @Test
    public void shouldNotDeriveQuestionValueOfGivenNullQuestionItem() {
        // Given
        QuestionItem questionItem = null;

        // When
        Question question = QuestionFactory.valueOf(questionItem);

        // Then
        assertThat(question)
                .isNull();
    }

    @Test
    public void shouldDeriveQuestionItemValueOfGivenQuestion() {
        // Given
        Question question = aSimpleQuestionAboutTriangles().build();
		String slug = "triangle-sides-question";
        QuestionItem expectedQuestionItem = aSimpleQuestionItemAboutTriangles()
				.id(question.getId())
				.slug(slug)
				.build();

        // When
        QuestionItem questionItem = QuestionFactory.valueOf(question, slug);

        // Then
        assertThat(questionItem)
                .isEqualTo(expectedQuestionItem);
    }

    @Test
    public void shouldNotDeriveQuestionItemValueOfGivenNullQuestion() {
        // Given
        Question question = null;
        String slug = "some-question";

        // When
        QuestionItem questionItem = QuestionFactory.valueOf(question, slug);

        // Then
        assertThat(questionItem)
                .isNull();
    }

}