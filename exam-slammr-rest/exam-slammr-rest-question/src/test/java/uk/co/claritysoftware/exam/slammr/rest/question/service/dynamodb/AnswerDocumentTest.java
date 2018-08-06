package uk.co.claritysoftware.exam.slammr.rest.question.service.dynamodb;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

/**
 * Unit test class for {@link AnswerDocument}
 */
public class AnswerDocumentTest {

    @Test
    public void shouldHonourEqualsHashcodeContract() {
        EqualsVerifier.forClass(AnswerDocument.class)
                .verify();
    }

}