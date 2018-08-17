package uk.co.claritysoftware.exam.slammr.rest.question.factory;

import org.springframework.stereotype.Component;
import uk.co.claritysoftware.exam.slammr.rest.question.service.dynamodb.AnswerDocument;
import uk.co.claritysoftware.exam.slammr.rest.question.service.dynamodb.FurtherReadingDocument;
import uk.co.claritysoftware.exam.slammr.rest.question.service.dynamodb.QuestionItem;
import uk.co.claritysoftware.exam.slammr.rest.question.web.model.QuestionCreateRequest;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Factory class to create {@link QuestionItem} instances
 */
@Component
public class QuestionItemFactory {

    public QuestionItem valueOf(QuestionCreateRequest questionCreateRequest, String identityId) {
        ZonedDateTime now = ZonedDateTime.now().withZoneSameInstant(ZoneOffset.UTC);
        return QuestionItem.builder()
                .questionText(questionCreateRequest.getQuestionText())
                .answers(answerDocuments(questionCreateRequest))
                .certifications(questionCreateRequest.getCertifications())
                .tags(questionCreateRequest.getTags())
                .furtherReadings(furtherReadingDocuments(questionCreateRequest))
                .createdBy(identityId)
                .createdDateTime(now)
                .build();
    }

    private List<AnswerDocument> answerDocuments(QuestionCreateRequest questionCreateRequest) {
        Set<Integer> correctAnswerNumbers = questionCreateRequest.getCorrectAnswers();
        List<AnswerDocument> answers = new ArrayList<>();
        int answerNumber = 1;
        for (String answerText : questionCreateRequest.getAnswers()) {
            answers.add(AnswerDocument.builder()
                    .text(answerText)
                    .correct(correctAnswerNumbers.contains(answerNumber))
                    .build());
            answerNumber++;
        }
        return answers;
    }

    private Set<FurtherReadingDocument> furtherReadingDocuments(QuestionCreateRequest questionCreateRequest) {
        return questionCreateRequest.getFurtherReadings().stream()
                .map(furtherReading -> FurtherReadingDocument.builder()
                        .description(furtherReading.getDescription())
                        .referenceLocation(furtherReading.getReferenceLocation())
                        .build())
                .collect(Collectors.toSet());
    }
}
