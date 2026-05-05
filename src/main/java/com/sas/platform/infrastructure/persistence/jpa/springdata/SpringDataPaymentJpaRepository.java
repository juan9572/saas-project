package com.sas.platform.infrastructure.persistence.jpa.springdata;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sas.platform.infrastructure.persistence.jpa.entities.PaymentJpaEntity;

public interface SpringDataPaymentJpaRepository extends JpaRepository<PaymentJpaEntity, UUID> {  
}
