package uk.co.claritysoftware.exam.slammr.webapp.web.controller

import org.assertj.core.api.Assertions.assertThat
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
 * MockMvc test class for {@link LoginPageController}
 */
@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("classpath:test-context.properties")
class LoginPageControllerMockMvcTest {

	@Autowired
	private lateinit var mvc: MockMvc

	@Test
	@Throws(Exception::class)
	fun shouldGetLoginPageGivenUnauthenticatedUser() {
		// Given
		val request = get("/login")

		// When
		val response = mvc.perform(request)
				.andReturn().response

		// Then
		assertThat(response.status)
				.`as`("HTTP Status code is 200")
				.isEqualTo(200)
	}

	@Test
	@Throws(Exception::class)
	fun shouldNotGetLoginPageGivenAuthenticatedUser() {
		// Given
		val request = get("/login")
				.with(user("aUser"))

		// When
		val response = mvc.perform(request)
				.andReturn().response

		// Then
		assertThat(response.status)
				.`as`("HTTP Status code is 302")
				.isEqualTo(302)
		assertThat(response.getHeader("Location"))
				.`as`("Location header is root")
				.isEqualTo("/")
	}
}