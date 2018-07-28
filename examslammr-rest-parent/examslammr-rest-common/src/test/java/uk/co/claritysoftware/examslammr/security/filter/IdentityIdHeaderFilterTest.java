package uk.co.claritysoftware.examslammr.security.filter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

import javax.servlet.FilterChain;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import uk.co.claritysoftware.examslammr.security.authentication.AwsCognitoAuthentication;

/**
 * Unit test class for {@link IdentityIdHeaderFilter}
 */
public class IdentityIdHeaderFilterTest {

    private IdentityIdHeaderFilter filter = new IdentityIdHeaderFilter();

    @Before
    public void resetSecurity() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    @Test
    public void shouldDoFilterGivenIdentityHeaderPresent() throws Exception {
        // Given
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain filterChain = mock(FilterChain.class);

        request.addHeader("x-exam-slammr-identityId", "12345");

        AwsCognitoAuthentication expectedAuthentication = new AwsCognitoAuthentication("12345");

        // When
        filter.doFilter(request, response, filterChain);

        // Then
        then(filterChain).should().doFilter(request, response);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assertThat(authentication)
                .as("Authentication is correctly set")
                .isEqualTo(expectedAuthentication);
    }

    @Test
    public void shouldDoFilterGivenIdentityHeaderNotPresent() throws Exception {
        // Given
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain filterChain = mock(FilterChain.class);

        // When
        filter.doFilter(request, response, filterChain);

        // Then
        then(filterChain).should().doFilter(request, response);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assertThat(authentication)
                .as("Authentication is not set")
                .isNull();
    }
}