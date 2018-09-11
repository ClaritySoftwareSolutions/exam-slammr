package uk.co.claritysoftware.exam.slammr.webapp.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import uk.co.claritysoftware.exam.slammr.webapp.service.UserProfileService;

/**
 * Unit test class for {@link ExamSlammrUserDetailsService}
 */
@RunWith(MockitoJUnitRunner.class)
public class ExamSlammrUserDetailsServiceTest {

	@Mock
	private UserProfileService userProfileService;

	@InjectMocks
	private ExamSlammrUserDetailsService userDetailsService;

	@Test
	public void shouldFailToLoadUserByUserIdGivenIdOfNonExistentUser(){
		// Given
		String userId = "12345";

		given(userProfileService.getUserProfileByUserId(any()))
				.willReturn(Optional.empty());
		// When
		Throwable throwable = catchThrowable(() -> userDetailsService.loadUserByUserId(userId));

		// Then
		assertThat(throwable)
				.isInstanceOf(UsernameNotFoundException.class)
				.hasMessage("UserProfile with id 12345 not found in repository");
	}
}