package uk.co.claritysoftware.exam.slammr.rest.question.web.contstraintvalidator;

import uk.co.claritysoftware.exam.slammr.rest.question.web.model.EditableQuestion;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Validates that a {@link EditableQuestion} has more answer choices than correct answers, and that all of the correct
 * answers are for valid questions.
 */
public class QuestionHasAnswersConstraintValidator implements ConstraintValidator<QuestionHasAnswers, EditableQuestion>

{
    @Override
    public void initialize(QuestionHasAnswers constraintAnnotation) {

    }

    @Override
    public boolean isValid(EditableQuestion editableQuestion, ConstraintValidatorContext constraintValidatorContext) {
        if (editableQuestion.getAnswers() == null || editableQuestion.getCorrectAnswers() == null) {
            return false;
        }

        int numberOfAnswerChoices = editableQuestion.getAnswers().size();
        int numberOfCorrectAnswers = editableQuestion.getCorrectAnswers().size();
        int lowestCorrectAnswerNumber = editableQuestion.getCorrectAnswers().stream()
                .mapToInt(Integer::intValue)
                .min().orElse(0);
        int highestCorrectAnswerNumber = editableQuestion.getCorrectAnswers().stream()
                .mapToInt(Integer::intValue)
                .max().orElse(0);

        return lowestCorrectAnswerNumber >= 1
                && highestCorrectAnswerNumber <= numberOfAnswerChoices + 1
                && numberOfAnswerChoices > numberOfCorrectAnswers;
    }
}
