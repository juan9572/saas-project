package com.sas.platform.infrastructure.persistence.jpa.springdata;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sas.platform.infrastructure.persistence.jpa.entities.InvoiceJpaEntity;

public interface SpringDataInvoiceJpaRepository extends JpaRepository<InvoiceJpaEntity, UUID> {
}
