package uk.co.claritysoftware.exam.slammr.webapp.web.controller

import org.assertj.core.api.Assertions.assertThat
import org.jsoup.Jsoup
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get

/**
 * MockMvc test class for {@link HomePageController}
 */
@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("classpath:test-context.properties")
class HomePageControllerMockMvcTest {

	@Autowired
	private lateinit var mvc: MockMvc

	@Test
	@Throws(Exception::class)
	fun shouldGetHomepageGivenUnauthenticatedUser() {
		// Given
		val request = get("/")

		// When
		val response = mvc.perform(request)
				.andReturn().response

		// Then
		val responseBody = response.contentAsString
		val htmlDocument = Jsoup.parse(responseBody)

		assertThat(response.status)
				.`as`("HTTP Status code is 200")
				.isEqualTo(200)
		assertThat(htmlDocument.selectFirst("#top-bar .user-profile-actions"))
				.`as`("Top bar does not contain user profile actions")
				.isNull()
		assertThat(htmlDocument.selectFirst("#top-bar li.ctas a[href=/login]"))
				.`as`("Top bar contains a login link")
				.isNotNull()
		assertThat(htmlDocument.selectFirst("#top-bar li.ctas a[href=/register]"))
				.`as`("Top bar contains a register link")
				.isNotNull()
		assertThat(htmlDocument.selectFirst(".home.hero.register"))
				.`as`("There is a hero section for registration")
				.isNotNull()
	}

	@Test
	@Throws(Exception::class)
	fun shouldGetHomepageGivenAuthenticatedUser() {
		// Given
		val request = get("/")
				.with(user("some-user"))

		// When
		val response = mvc.perform(request)
				.andReturn().response

		// Then
		val responseBody = response.contentAsString
		val htmlDocument = Jsoup.parse(responseBody)

		assertThat(response.status)
				.`as`("HTTP Status code is 200")
				.isEqualTo(200)
		assertThat(htmlDocument.selectFirst("#top-bar .user-profile-actions"))
				.`as`("Top bar contains user profile actions")
				.isNotNull()
		assertThat(htmlDocument.selectFirst("#top-bar li.ctas a[href=/login]"))
				.`as`("Top bar does not contain a login link")
				.isNull()
		assertThat(htmlDocument.selectFirst("#top-bar li.ctas a[href=/register]"))
				.`as`("Top bar does not contain a register link")
				.isNull()
		assertThat(htmlDocument.selectFirst(".home.hero.register"))
				.`as`("There is not a hero section for registration")
				.isNull()
	}


}