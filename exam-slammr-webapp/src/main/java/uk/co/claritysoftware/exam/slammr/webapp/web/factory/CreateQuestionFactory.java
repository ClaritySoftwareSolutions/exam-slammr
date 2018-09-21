package uk.co.claritysoftware.exam.slammr.webapp.web.factory;

import static com.google.common.collect.Sets.newHashSet;
import static uk.co.claritysoftware.exam.slammr.webapp.service.model.question.QuestionStatus.SUBMITTED_FOR_APPROVAL;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.stream.Collectors;
import uk.co.claritysoftware.exam.slammr.webapp.service.model.question.AnswerOption;
import uk.co.claritysoftware.exam.slammr.webapp.service.model.question.FurtherReading;
import uk.co.claritysoftware.exam.slammr.webapp.service.model.question.Question;
import uk.co.claritysoftware.exam.slammr.webapp.web.model.question.CreateQuestion;

/**
 * Factory class to create instances to and from {@link CreateQuestion} instances
 */
public class CreateQuestionFactory {

    public static Question valueOf(CreateQuestion createQuestion, String authorId) {
        ZonedDateTime now = ZonedDateTime.now().withZoneSameInstant(ZoneOffset.UTC);
        return createQuestion != null ?
                Question.builder()
                        .id(null)
						.slug(null)
                        .summary(createQuestion.getSummary())
                        .questionText(createQuestion.getQuestion())
                        .answers(createQuestion.getAnswerOptions().stream()
                                .map(answerOption -> AnswerOption.builder()
                                        .text(answerOption.getAnswer())
                                        .correct(answerOption.isCorrect())
                                        .build())
                                .collect(Collectors.toList()))
                        .tags(newHashSet(createQuestion.getTags()))
                        .certifications(newHashSet(createQuestion.getCertifications()))
                        .furtherReadings(createQuestion.getFurtherReadings().stream()
                                .map(furtherReading -> FurtherReading.builder()
                                        .description(furtherReading.getDescription())
                                        .referenceLocation(furtherReading.getReferenceLocation())
                                        .build())
                                .collect(Collectors.toSet()))
                        .status(SUBMITTED_FOR_APPROVAL)
                        .votes(0)
                        .createdBy(authorId)
                        .createdDateTime(now)
                        .updatedBy(null)
                        .updatedDateTime(null)
                        .build() : null;
    }
}
