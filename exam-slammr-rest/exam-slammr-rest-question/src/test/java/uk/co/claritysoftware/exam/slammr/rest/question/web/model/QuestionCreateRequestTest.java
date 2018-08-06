package uk.co.claritysoftware.exam.slammr.rest.question.web.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

/**
 * Unit test class for {@link QuestionCreateRequest}
 */
public class QuestionCreateRequestTest {

    @Test
    public void shouldHonourEqualsHashcodeContract() {
        EqualsVerifier.forClass(QuestionCreateRequest.class)
                .verify();
    }

}