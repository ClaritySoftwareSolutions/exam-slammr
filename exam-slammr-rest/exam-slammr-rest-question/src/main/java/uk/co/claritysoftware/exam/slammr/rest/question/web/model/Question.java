package uk.co.claritysoftware.exam.slammr.rest.question.web.model;

import java.util.Set;

/**
 * Base interface for a Question
 */
public interface Question {

    /**
     * @return the question text
     */
    String getQuestionText();

    /**
     * @return the set of answers
     */
    Set<String> getAnswers();

    /**
     * @return the set of correct answers
     */
    Set<Integer> getCorrectAnswers();
}
