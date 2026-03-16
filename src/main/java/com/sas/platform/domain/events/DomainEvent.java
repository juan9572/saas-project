package com.sas.platform.domain.events;

import java.time.ZonedDateTime;

public interface DomainEvent {
    ZonedDateTime getOccurredAt();
}