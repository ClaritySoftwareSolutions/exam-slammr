package uk.co.claritysoftware.exam.slammr.webapp.service.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public final class AnswerOption {

    private String text;

    private boolean correct;
}
