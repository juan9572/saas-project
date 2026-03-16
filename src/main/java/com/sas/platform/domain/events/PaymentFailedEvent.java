package com.sas.platform.domain.events;

import java.time.ZonedDateTime;
import java.util.UUID;

public class PaymentFailedEvent implements DomainEvent {
    private final UUID invoiceId;
    private final int attemptNumber;
    private final ZonedDateTime occurredAt;

    public PaymentFailedEvent(
        UUID invoiceId,
        int attemptNumber,
        ZonedDateTime occurredAt
    ) {
        this.invoiceId = invoiceId;
        this.attemptNumber = attemptNumber;
        this.occurredAt = occurredAt;
    }

    public UUID getInvoiceId() {
        return this.invoiceId;
    }

    public int getAttemptNumber() {
        return this.attemptNumber;
    }

    @Override
    public ZonedDateTime getOccurredAt() {
        return this.occurredAt;
    }
}
