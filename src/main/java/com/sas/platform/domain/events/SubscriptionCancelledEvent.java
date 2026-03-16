package com.sas.platform.domain.events;

import java.time.ZonedDateTime;
import java.util.UUID;

public class SubscriptionCancelledEvent implements DomainEvent {
    private final UUID subscriptionId;
    private final ZonedDateTime occurredAt;

    public SubscriptionCancelledEvent(
        UUID subscriptionId,
        ZonedDateTime occurredAt
    ) {
        this.subscriptionId = subscriptionId;
        this.occurredAt = occurredAt;
    }

    public UUID getSubscriptionId() {
        return this.subscriptionId;
    }

    @Override
    public ZonedDateTime getOccurredAt() {
        return this.occurredAt;
    }
}
