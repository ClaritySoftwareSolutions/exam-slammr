package uk.co.claritysoftware.exam.slammr.web.interceptor;

import static org.assertj.core.api.Assertions.assertThat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.AfterClass;
import org.junit.Test;
import org.slf4j.MDC;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import uk.co.claritysoftware.exam.slammr.security.authentication.AwsCognitoAuthentication;

/**
 * Unit test class for {@link IdentityIdMdcInterceptor}
 */
public class IdentityIdMdcInterceptorTest {

    private IdentityIdMdcInterceptor interceptor = new IdentityIdMdcInterceptor();

    @AfterClass
    public static void leaveMdcClear() {
        MDC.clear();
    }

    @AfterClass
    public static void removeSecurityContext() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    @Test
    public void shouldPreHandle() {
        // Given
        HttpServletRequest request = new MockHttpServletRequest();
        HttpServletResponse response = new MockHttpServletResponse();
        Object handler = new Object();

        String identityId = "12345";
        SecurityContextHolder.getContext().setAuthentication(new AwsCognitoAuthentication(identityId));
        MDC.clear();

        // When
        interceptor.preHandle(request, response, handler);

        // Then
        assertThat(MDC.get("identityId"))
                .as("Expected identityId to be an MDC value")
                .isEqualTo(identityId);

    }
}