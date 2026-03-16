package com.sas.platform.domain.events;

import java.time.ZonedDateTime;
import java.util.UUID;

public class GracePeriodExpiredEvent implements DomainEvent {
    private final UUID subscriptionId;
    private final ZonedDateTime occurredAt;

    public GracePeriodExpiredEvent(
        UUID subscriptionId,
        ZonedDateTime occurredAt
    ) {
        this.subscriptionId = subscriptionId;
        this.occurredAt = occurredAt;
    }

    public UUID getSubscriptionId() {
        return subscriptionId;
    }

    @Override
    public ZonedDateTime getOccurredAt() {
        return occurredAt;
    }
}