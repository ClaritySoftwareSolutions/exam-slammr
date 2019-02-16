package uk.co.claritysoftware.exam.slammr.webapp.security

import com.nhaarman.mockitokotlin2.any
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.springframework.security.core.userdetails.UsernameNotFoundException
import uk.co.claritysoftware.exam.slammr.webapp.service.UserProfileService
import java.util.*

/**
 * Unit test class for [ExamSlammrUserDetailsService]
 */
@RunWith(MockitoJUnitRunner::class)
class ExamSlammrUserDetailsServiceTest {

	@Mock
	private val userProfileService: UserProfileService? = null

	@InjectMocks
	private val userDetailsService: ExamSlammrUserDetailsService? = null

	@Test
	fun shouldFailToLoadUserByUserIdGivenIdOfNonExistentUser() {
		// Given
		val userId = "12345"

		given(userProfileService!!.getUserProfileByUserId(any()))
				.willReturn(Optional.empty())
		// When
		val throwable = catchThrowable { userDetailsService!!.loadUserByUserId(userId) }

		// Then
		assertThat(throwable)
				.isInstanceOf(UsernameNotFoundException::class.java)
				.hasMessage("UserProfile with id 12345 not found in repository")
	}
}