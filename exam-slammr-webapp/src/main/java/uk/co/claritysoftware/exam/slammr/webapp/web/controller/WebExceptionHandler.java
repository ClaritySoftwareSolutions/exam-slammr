package uk.co.claritysoftware.exam.slammr.webapp.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import uk.co.claritysoftware.exam.slammr.webapp.web.exception.QuestionNotFoundException;

import lombok.extern.slf4j.Slf4j;

/**
 * Exception handler for exceptions thrown by the web dispatcher servlet
 */
@Slf4j
@ControllerAdvice
public class WebExceptionHandler {

	@ExceptionHandler(QuestionNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ModelAndView handleQuestionNotFoundException(QuestionNotFoundException e) {
		log.debug("Handling QuestionNotFoundException: {}", e.getMessage());
		return new ModelAndView("404");
	}
}
