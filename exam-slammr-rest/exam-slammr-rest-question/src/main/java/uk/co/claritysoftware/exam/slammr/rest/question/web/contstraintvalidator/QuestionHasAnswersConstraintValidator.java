package uk.co.claritysoftware.exam.slammr.rest.question.web.contstraintvalidator;

import uk.co.claritysoftware.exam.slammr.rest.question.web.model.Question;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Validates that a Question instance has more answer choices than correct answers, and that all of the correct
 * answers are for valid questions.
 */
public class QuestionHasAnswersConstraintValidator implements ConstraintValidator<QuestionHasAnswers, Question>

{
    @Override
    public void initialize(QuestionHasAnswers constraintAnnotation) {

    }

    @Override
    public boolean isValid(Question question, ConstraintValidatorContext constraintValidatorContext) {
        if (question.getAnswers() == null || question.getCorrectAnswers() == null) {
            return false;
        }

        int numberOfAnswerChoices = question.getAnswers().size();
        int numberOfCorrectAnswers = question.getCorrectAnswers().size();
        int lowestCorrectAnswerNumber = question.getCorrectAnswers().stream()
                .mapToInt(Integer::intValue)
                .min().orElse(0);
        int highestCorrectAnswerNumber = question.getCorrectAnswers().stream()
                .mapToInt(Integer::intValue)
                .max().orElse(0);

        return lowestCorrectAnswerNumber >= 1
                && highestCorrectAnswerNumber <= numberOfAnswerChoices + 1
                && numberOfAnswerChoices > numberOfCorrectAnswers;
    }
}
