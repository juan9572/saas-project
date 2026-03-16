package com.sas.platform.domain.events;

import java.time.ZonedDateTime;
import java.util.UUID;

public class GracePeriodEnteredEvent implements DomainEvent {
    private final UUID subscriptionId;
    private final ZonedDateTime graceEndsAt;
    private final ZonedDateTime occurredAt;

    public GracePeriodEnteredEvent(
        UUID subscriptionId,
        ZonedDateTime graceEndsAt,
        ZonedDateTime occurredAt
    ) {
        this.subscriptionId = subscriptionId;
        this.graceEndsAt = graceEndsAt;
        this.occurredAt = occurredAt;
    }

    public UUID getSubscriptionId() {
        return subscriptionId;
    }

    public ZonedDateTime getGraceEndsAt() {
        return graceEndsAt;
    }

    @Override
    public ZonedDateTime getOccurredAt() {
        return occurredAt;
    }
}