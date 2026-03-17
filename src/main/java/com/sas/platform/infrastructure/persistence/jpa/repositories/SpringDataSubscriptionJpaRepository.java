package com.sas.platform.infrastructure.persistence.jpa.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sas.platform.infrastructure.persistence.jpa.entities.SubscriptionJpaEntity;

public interface SpringDataSubscriptionJpaRepository extends JpaRepository<SubscriptionJpaEntity, UUID> {
}
