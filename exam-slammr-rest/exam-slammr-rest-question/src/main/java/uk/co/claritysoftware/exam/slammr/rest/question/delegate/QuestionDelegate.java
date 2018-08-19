package uk.co.claritysoftware.exam.slammr.rest.question.delegate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.co.claritysoftware.exam.slammr.rest.question.exception.QuestionCreateException;
import uk.co.claritysoftware.exam.slammr.rest.question.factory.QuestionFactory;
import uk.co.claritysoftware.exam.slammr.rest.question.factory.QuestionItemFactory;
import uk.co.claritysoftware.exam.slammr.rest.question.service.QuestionService;
import uk.co.claritysoftware.exam.slammr.rest.question.service.dynamodb.QuestionItem;
import uk.co.claritysoftware.exam.slammr.rest.question.web.model.EditableQuestion;
import uk.co.claritysoftware.exam.slammr.rest.question.web.model.Question;

import lombok.extern.slf4j.Slf4j;

/**
 * Delegate class for Question concerns
 */
@Slf4j
@Component
public class QuestionDelegate {

	private final QuestionService questionService;

	private final QuestionFactory questionFactory;

	private final QuestionItemFactory questionItemFactory;

	@Autowired
	public QuestionDelegate(QuestionService questionService, QuestionFactory questionFactory, QuestionItemFactory questionItemFactory) {
		this.questionService = questionService;
		this.questionFactory = questionFactory;
		this.questionItemFactory = questionItemFactory;
	}

	public Question getQuestion(String questionId) {
		log.debug("Get Question with id {}", questionId);

		return questionFactory.valueOf(questionService.getQuestion(questionId)
				.orElseThrow(() -> new RuntimeException()));
	}

	/**
	 * Saves a new Question and returns it's id
	 *
	 * @param editableQuestion the new question request from the client to save
	 * @param identityId       the identity id of the user creating the new question
	 * @return the id of the new question
	 */
	public String createQuestion(EditableQuestion editableQuestion, String identityId) {
		log.debug("Create Question with request {}", editableQuestion);

		QuestionItem unsavedQuestionItem = questionItemFactory.unsavedQuestionItem(editableQuestion, identityId);
		return questionService.createQuestion(unsavedQuestionItem)
				.orElseThrow(() -> new QuestionCreateException(editableQuestion));
	}
}
