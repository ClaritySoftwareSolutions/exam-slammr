package uk.co.claritysoftware.exam.slammr.security.authentication;

import static uk.co.claritysoftware.exam.slammr.web.testsupport.assertj.MockHttpServletResponseAssert.assertThat;

import java.io.IOException;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * Unit test class for {@link UnauthorisedAuthenticationEntryPoint}
 */
public class UnauthorisedAuthenticationEntryPointTest {

    private UnauthorisedAuthenticationEntryPoint entryPoint = new UnauthorisedAuthenticationEntryPoint();

    @Test
    public void shouldCommence() throws IOException {
        // Given
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        // When
        entryPoint.commence(request, response, null);

        // Then
        assertThat(response)
                .as("HTTP status code is 401")
                .hasStatusCode(HttpStatus.UNAUTHORIZED);
    }
}