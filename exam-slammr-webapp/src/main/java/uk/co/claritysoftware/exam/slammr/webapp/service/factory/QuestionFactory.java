package uk.co.claritysoftware.exam.slammr.webapp.service.factory;


import uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.item.question.AnswerDocument;
import uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.item.question.FurtherReadingDocument;
import uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.item.question.QuestionItem;
import uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.item.question.QuestionStatus;
import uk.co.claritysoftware.exam.slammr.webapp.service.model.question.AnswerOption;
import uk.co.claritysoftware.exam.slammr.webapp.service.model.question.FurtherReading;
import uk.co.claritysoftware.exam.slammr.webapp.service.model.question.Question;

import java.util.stream.Collectors;

/**
 * Factory class to create instances to and from {@link Question} instances
 */
public class QuestionFactory {

    /**
     * Creates a new {@link Question} from the specified {@link QuestionItem}
     *
     * @param questionItem the QuestionItem to create the Question from
     * @return a populated Question instance
     */
    public static Question valueOf(QuestionItem questionItem) {
        return questionItem != null ? Question.builder()
                .id(questionItem.getId())
                .questionText(questionItem.getQuestionText())
                .answers(questionItem.getAnswers().stream()
                        .map(answerDocument -> AnswerOption.builder()
                                .text(answerDocument.getText())
                                .correct(answerDocument.isCorrect())
                                .build())
                        .collect(Collectors.toList()))
                .tags(questionItem.getTags())
                .certifications(questionItem.getCertifications())
                .furtherReadings(questionItem.getFurtherReadings().stream()
                        .map(furtherReadingDocument -> FurtherReading.builder()
                                .description(furtherReadingDocument.getDescription())
                                .referenceLocation(furtherReadingDocument.getReferenceLocation())
                                .build())
                        .collect(Collectors.toSet()))
                .status(questionItem.getStatus().name())
                .votes(questionItem.getVotes())
                .createdBy(questionItem.getCreatedBy())
                .createdDateTime(questionItem.getCreatedDateTime())
                .updatedBy(questionItem.getUpdatedBy())
                .updatedDateTime(questionItem.getUpdatedDateTime())
                .build() : null;
    }

    /**
     * Creates a new {@link QuestionItem} from the specified {@link Question}
     *
     * @param question the Question to create the QuestionItem from
     * @return a populated QuestionItem instance
     */
    public static QuestionItem valueOf(Question question) {
        return question != null ? QuestionItem.builder()
                .id(question.getId())
                .questionText(question.getQuestionText())
                .answers(question.getAnswers().stream()
                        .map(answerOption -> AnswerDocument.builder()
                                .text(answerOption.getText())
                                .correct(answerOption.isCorrect())
                                .build())
                        .collect(Collectors.toList()))
                .tags(question.getTags())
                .certifications(question.getCertifications())
                .furtherReadings(question.getFurtherReadings().stream()
                        .map(furtherReading -> FurtherReadingDocument.builder()
                                .description(furtherReading.getDescription())
                                .referenceLocation(furtherReading.getReferenceLocation())
                                .build())
                        .collect(Collectors.toSet()))
                .status(QuestionStatus.valueOf(question.getStatus()))
                .votes(question.getVotes())
                .createdBy(question.getCreatedBy())
                .createdDateTime(question.getCreatedDateTime())
                .updatedBy(question.getUpdatedBy())
                .updatedDateTime(question.getUpdatedDateTime())
                .build() : null;
    }

}
