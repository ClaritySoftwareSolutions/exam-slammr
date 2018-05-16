package uk.co.claritysoftware.exam.slammr.rest.testsupport.assertj;

import org.assertj.core.api.AbstractAssert;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * AssertJ assert class to make fluent assertions on Spring's {@link MockHttpServletResponse}
 */
public class MockHttpServletResponseAssert extends AbstractAssert<MockHttpServletResponseAssert, MockHttpServletResponse> {

    private MockHttpServletResponseAssert(MockHttpServletResponse actual) {
        super(actual, MockHttpServletResponseAssert.class);
    }

    public static MockHttpServletResponseAssert assertThat(MockHttpServletResponse actual) {
        return new MockHttpServletResponseAssert(actual);
    }

    /**
     * Asserts that the MockHttpServletResponse has the expected http status code
     *
     * @param expectedStatusCode the expected http status code
     * @return this MockHttpServletResponseAssert instance for further chaining
     */
    public MockHttpServletResponseAssert hasStatusCode(int expectedStatusCode) {
        isNotNull();

        if (actual.getStatus() != expectedStatusCode) {
            failWithMessage("Expected response to have status code %s, but was %s", expectedStatusCode, actual.getStatus());
        }

        return this;
    }

    /**
     * Asserts that the MockHttpServletResponse has the expected http status
     *
     * @param expectedStatus the expected http status
     * @return this MockHttpServletResponseAssert instance for further chaining
     */
    public MockHttpServletResponseAssert hasStatusCode(HttpStatus expectedStatus) {
        return hasStatusCode(expectedStatus.value());
    }

    /**
     * Asserts that the MockHttpServletResponse has the expected content type
     *
     * @param contentType the expected content type
     * @return this MockHttpServletResponseAssert instance for further chaining
     */
    public MockHttpServletResponseAssert hasContentType(MediaType contentType) {
        isNotNull();

        if (actual.getContentType() != contentType.toString()) {
            failWithMessage("Expected response to have content type %s, but was %s", contentType, actual.getContentType());
        }

        return this;
    }

}
