package uk.co.claritysoftware.exam.slammr.webapp.web.controller

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import uk.co.claritysoftware.exam.slammr.webapp.service.QuestionService
import uk.co.claritysoftware.exam.slammr.webapp.web.exception.QuestionNotFoundException
import uk.co.claritysoftware.exam.slammr.webapp.web.factory.CreateQuestionFactory.Companion.valueOf
import uk.co.claritysoftware.exam.slammr.webapp.web.model.question.AnswerOption
import uk.co.claritysoftware.exam.slammr.webapp.web.model.question.CreateQuestion
import uk.co.claritysoftware.exam.slammr.webapp.web.model.question.FurtherReading
import java.security.Principal
import javax.validation.Valid

/**
 * Controller to handle question page requests
 */
@Controller
@RequestMapping("/question")
class QuestionController(@Autowired val questionService: QuestionService) {

	private companion object {
		val log = LoggerFactory.getLogger(QuestionController::class.java)
	}

	@GetMapping("/new")
	fun getQuestionPage(): ModelAndView {
		return createQuestionViewHidingBindErrors(CreateQuestion())
	}

	@PostMapping
	fun createNewQuestion(@Valid @ModelAttribute("form") form: CreateQuestion, bindingResult: BindingResult, principal: Principal): ModelAndView {

		when (form.action) {
			CreateQuestion.Action.addCertification -> {
				form.certifications.add("")
				return createQuestionViewHidingBindErrors(form)
			}

			CreateQuestion.Action.addTag -> {
				form.tags.add("")
				return createQuestionViewHidingBindErrors(form)
			}

			CreateQuestion.Action.addFurtherReading -> {
				form.furtherReadings.add(FurtherReading())
				return createQuestionViewHidingBindErrors(form)
			}

			CreateQuestion.Action.addAnswer -> {
				form.answerOptions.add(AnswerOption())
				return createQuestionViewHidingBindErrors(form)
			}

			else -> {

				if (bindingResult.hasErrors()) {
					return createQuestionViewShowingBindErrors(form)
				}

				val newQuestion = valueOf(form, principal.name)
				val (id, slug) = questionService.saveNewQuestion(newQuestion)

				return ModelAndView("redirect:/question/$id/$slug")
			}
		}
	}

	@GetMapping("/{id}/{slug}")
	fun getExistingQuestionPage(@PathVariable("id") id: String, @PathVariable("slug") slug: String?): ModelAndView {
		val retrievedQuestion = questionService.getQuestionById(id)
		if (!retrievedQuestion.isPresent) {
			log.warn("Question with id {} could not be found", id)
			throw QuestionNotFoundException(id)
		}

		val question = retrievedQuestion.get()

		if (question.slug != slug) {
			val redirectUrl = "redirect:/question/" + id + "/" + question.slug
			log.warn("Question with id {} does not have requested slug {}. Redirecting to {}", id, slug, redirectUrl)
			return ModelAndView(redirectUrl)
		}

		val modelAndView = ModelAndView("question/view")
		modelAndView.addObject("question", question)
		return modelAndView
	}

	@GetMapping("/{id}")
	fun getExistingQuestionPageWithoutSlug(@PathVariable("id") id: String): ModelAndView {
		return getExistingQuestionPage(id, null)
	}

	private fun createQuestionViewShowingBindErrors(form: CreateQuestion): ModelAndView {
		val modelAndView = ModelAndView("question/create")
		modelAndView.addObject("showBindErrors", true)
		modelAndView.addObject("form", form)
		return modelAndView
	}

	private fun createQuestionViewHidingBindErrors(form: CreateQuestion): ModelAndView {
		val modelAndView = ModelAndView("question/create")
		modelAndView.addObject("showBindErrors", false)
		modelAndView.addObject("form", form)
		return modelAndView
	}

}