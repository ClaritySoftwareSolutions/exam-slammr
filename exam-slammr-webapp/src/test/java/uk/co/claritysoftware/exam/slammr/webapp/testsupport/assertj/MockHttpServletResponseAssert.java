package uk.co.claritysoftware.exam.slammr.webapp.testsupport.assertj;

import java.io.IOException;
import org.assertj.core.api.AbstractAssert;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

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
     * @param expectedContentType the expected content type
     * @return this MockHttpServletResponseAssert instance for further chaining
     */
    public MockHttpServletResponseAssert hasContentType(MediaType expectedContentType) {
        isNotNull();

        MediaType actualContentType = MediaType.valueOf(actual.getContentType());
        if (!actualContentType.equals(expectedContentType)) {
            failWithMessage("Expected response to have content type %s, but was %s", expectedContentType, actual.getContentType());
        }

        return this;
    }

    /**
     * Asserts that the MockHttpServletResponse has the expected body
     *
     * @param expectedBody the expected body
     * @param objectMapper the object mapper to deserialize the response body with
     * @return this MockHttpServletResponseAssert instance for further chaining
     */
    public MockHttpServletResponseAssert hasBody(Object expectedBody, ObjectMapper objectMapper) throws IOException {
        isNotNull();

        Object actualBody = objectMapper.readValue(actual.getContentAsString(), expectedBody.getClass());
        if (!actualBody.equals(expectedBody)) {
            failWithMessage("Expected response to have body %s, but was %s", expectedBody, actualBody);
        }
        return this;
    }

    /**
     * Asserts that the MockHttpServletResponse has no body
     *
     * @return this MockHttpServletResponseAssert instance for further chaining
     */
    public MockHttpServletResponseAssert hasNoBody() throws IOException {
        isNotNull();

        String actualBody = actual.getContentAsString();
        if (actualBody.length() > 0) {
            failWithMessage("Expected response to have no body, but it was %s", actualBody);
        }
        return this;
    }

}
