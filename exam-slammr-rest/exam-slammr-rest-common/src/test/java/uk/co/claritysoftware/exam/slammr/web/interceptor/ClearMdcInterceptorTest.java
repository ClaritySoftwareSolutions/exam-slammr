package uk.co.claritysoftware.exam.slammr.web.interceptor;

import org.junit.AfterClass;
import org.junit.Test;
import org.slf4j.MDC;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit test class for {@link ClearMdcInterceptor}
 */
public class ClearMdcInterceptorTest {

    private ClearMdcInterceptor interceptor = new ClearMdcInterceptor();

    @AfterClass
    public static void leaveMdcClear() {
        MDC.clear();
    }

    @Test
    public void shouldAfterCompletion() {
        // Given
        HttpServletRequest request = new MockHttpServletRequest();
        HttpServletResponse response = new MockHttpServletResponse();
        Object handler = new Object();
        Exception exception = null;

        MDC.put("some-key", "some-value");

        // When
        interceptor.afterCompletion(request, response, handler, exception);

        // Then
        assertThat(MDC.get("some-key"))
                .as("Expected no MDC value for key 'some-key'")
                .isNull();
    }
}