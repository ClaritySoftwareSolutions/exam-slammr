package uk.co.claritysoftware.exam.slammr.rest.question.factory;

import org.springframework.stereotype.Component;
import uk.co.claritysoftware.exam.slammr.rest.question.service.dynamodb.AnswerDocument;
import uk.co.claritysoftware.exam.slammr.rest.question.service.dynamodb.FurtherReadingDocument;
import uk.co.claritysoftware.exam.slammr.rest.question.service.dynamodb.QuestionItem;
import uk.co.claritysoftware.exam.slammr.rest.question.web.model.EditableQuestion;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static uk.co.claritysoftware.exam.slammr.rest.question.service.dynamodb.QuestionStatus.UNAPPROVED;

/**
 * Factory class to create {@link QuestionItem} instances
 */
@Component
public class QuestionItemFactory {

    /**
     * Returns a {@link QuestionItem} representing a new unsaved Question populated with fields from the specified
     * {@link EditableQuestion}. The fields createdBy and updatedBy are both set from the specified identityId,
     * and the fields createdDateTime and updatedDateTime are both set to 'now'. The status is set to UNAPPROVED, and
     * the number of votes is set to 0
     *
     * @param editableQuestion the {@link EditableQuestion} to set the question centric fields from
     * @param identityId       the identity id of the user creating this question
     * @return a QuestionItem setup a new unsaved Question suitable for saving to DynamoDB
     */
    public QuestionItem unsavedQuestionItem(EditableQuestion editableQuestion, String identityId) {
        ZonedDateTime now = now();
        return QuestionItem.builder()
                .questionText(editableQuestion.getQuestionText())
                .answers(answerDocuments(editableQuestion))
                .certifications(editableQuestion.getCertifications())
                .tags(editableQuestion.getTags())
                .furtherReadings(furtherReadingDocuments(editableQuestion))
                .createdBy(identityId)
                .createdDateTime(now)
                .updatedBy(identityId)
                .updatedDateTime(now)
                .status(UNAPPROVED)
                .votes(0)
                .build();
    }

    private static ZonedDateTime now() {
        return ZonedDateTime.now().withZoneSameInstant(ZoneOffset.UTC);
    }

    private static List<AnswerDocument> answerDocuments(EditableQuestion editableQuestion) {
        Set<Integer> correctAnswerNumbers = editableQuestion.getCorrectAnswers();
        List<AnswerDocument> answers = new ArrayList<>();
        int answerNumber = 1;
        for (String answerText : editableQuestion.getAnswers()) {
            answers.add(AnswerDocument.builder()
                    .text(answerText)
                    .correct(correctAnswerNumbers.contains(answerNumber))
                    .build());
            answerNumber++;
        }
        return answers;
    }

    private static Set<FurtherReadingDocument> furtherReadingDocuments(EditableQuestion editableQuestion) {
        return editableQuestion.getFurtherReadings().stream()
                .map(furtherReading -> FurtherReadingDocument.builder()
                        .description(furtherReading.getDescription())
                        .referenceLocation(furtherReading.getReferenceLocation())
                        .build())
                .collect(Collectors.toSet());
    }
}
