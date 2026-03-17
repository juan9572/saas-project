package com.sas.platform.domain.repositories;

import java.util.Optional;
import java.util.UUID;

import com.sas.platform.domain.entities.subscription.Subscription;

public interface SubscriptionRepository {
    Optional<Subscription> findById(UUID id);
    void save(Subscription subscription);
    void delete(UUID id);
}
