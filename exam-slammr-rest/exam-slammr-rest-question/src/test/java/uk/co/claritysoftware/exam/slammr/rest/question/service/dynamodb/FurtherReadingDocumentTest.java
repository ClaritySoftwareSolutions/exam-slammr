package uk.co.claritysoftware.exam.slammr.rest.question.service.dynamodb;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

/**
 * Unit test class for {@link FurtherReadingDocument}
 */
public class FurtherReadingDocumentTest {

    @Test
    public void shouldHonourEqualsHashcodeContract() {
        EqualsVerifier.forClass(FurtherReadingDocument.class)
                .verify();
    }

}