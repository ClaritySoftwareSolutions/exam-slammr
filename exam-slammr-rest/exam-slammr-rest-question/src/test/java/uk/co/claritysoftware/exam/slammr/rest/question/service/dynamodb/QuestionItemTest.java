package uk.co.claritysoftware.exam.slammr.rest.question.service.dynamodb;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

/**
 * Unit test class for {@link QuestionItem}
 */
public class QuestionItemTest {

    @Test
    public void shouldHonourEqualsHashcodeContract() {
        EqualsVerifier.forClass(QuestionItem.class)
                .verify();
    }

}