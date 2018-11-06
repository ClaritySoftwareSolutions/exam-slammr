package uk.co.claritysoftware.exam.slammr.webapp.web.exception

/**
 * RuntimeException for when a Question can not be found
 */
class QuestionNotFoundException(questionId: String) : RuntimeException("Question with id $questionId not found")
