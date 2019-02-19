package uk.co.claritysoftware.exam.slammr.webapp.web.controller

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.servlet.ModelAndView
import uk.co.claritysoftware.exam.slammr.webapp.web.exception.QuestionNotFoundException

/**
 * Exception handler for exceptions thrown by the web dispatcher servlet
 */
@ControllerAdvice
class WebExceptionHandler {

	private companion object {
		val log = LoggerFactory.getLogger(WebExceptionHandler::class.java)
	}

	@ExceptionHandler(QuestionNotFoundException::class)
	@ResponseStatus(NOT_FOUND)
	fun handleQuestionNotFoundException(e: QuestionNotFoundException): ModelAndView {
		log.debug("Handling QuestionNotFoundException: {}", e.message)
		return ModelAndView("404")
	}

}