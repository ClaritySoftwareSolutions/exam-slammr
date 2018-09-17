package uk.co.claritysoftware.exam.slammr.webapp.web.factory;

import org.junit.Test;
import uk.co.claritysoftware.exam.slammr.webapp.service.model.question.Question;
import uk.co.claritysoftware.exam.slammr.webapp.web.model.question.CreateQuestion;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.claritysoftware.exam.slammr.webapp.service.model.question.QuestionStatus.SUBMITTED_FOR_APPROVAL;
import static uk.co.claritysoftware.exam.slammr.webapp.testsupport.service.model.question.QuestionTestDataFactory.aSimpleQuestionAboutTriangles;
import static uk.co.claritysoftware.exam.slammr.webapp.testsupport.web.model.question.CreateQuestionTestDataFactory.aSimpleCreateQuestionAboutTriangles;

/**
 * Unit test class for {@link CreateQuestionFactory}
 */
public class CreateQuestionFactoryTest {

    @Test
    public void shouldDeriveQuestionItemValueOfGivenQuestion() {
        // Given
        CreateQuestion createQuestion = aSimpleCreateQuestionAboutTriangles().build();
        String authorId = "1234";
        Question expectedQuestion = aSimpleQuestionAboutTriangles()
                .id(null)
                .createdBy(authorId)
                .updatedBy(null)
                .updatedDateTime(null)
                .status(SUBMITTED_FOR_APPROVAL)
                .votes(0)
                .build();

        ZonedDateTime earliestCreatedDateTime = ZonedDateTime.now().withZoneSameInstant(ZoneOffset.UTC);

        // When
        Question question = CreateQuestionFactory.valueOf(createQuestion, authorId);

        // Then
        ZonedDateTime latestCreatedDateTime = ZonedDateTime.now().withZoneSameInstant(ZoneOffset.UTC);
        assertThat(question)
                .isEqualToIgnoringGivenFields(expectedQuestion, "createdDateTime");
        assertThat(question.getCreatedDateTime())
                .isAfterOrEqualTo(earliestCreatedDateTime)
                .isBeforeOrEqualTo(latestCreatedDateTime);
    }

    @Test
    public void shouldNotDeriveQuestionValueOfGivenNullCreateQuestion() {
        // Given
        CreateQuestion createQuestion = null;
        String authorId = "1234";

        // When
        Question question = CreateQuestionFactory.valueOf(createQuestion, authorId);

        // Then
        assertThat(question)
                .isNull();
    }

}