package com.sas.platform.domain.events;

import java.time.ZonedDateTime;
import java.util.UUID;

public class PaymentSucceededEvent implements DomainEvent {
    private final UUID paymentId;
    private final UUID invoiceId;
    private final ZonedDateTime occurredAt;

    public PaymentSucceededEvent(
        UUID paymentId,
        UUID invoiceId,
        ZonedDateTime occurredAt
    ) {
        this.paymentId = paymentId;
        this.invoiceId = invoiceId;
        this.occurredAt = occurredAt;
    }

    public UUID getPaymentId() {
        return this.paymentId;
    }

    public UUID getInvoiceId() {
        return this.invoiceId;
    }

    @Override
    public ZonedDateTime getOccurredAt() {
        return this.occurredAt;
    }
}
