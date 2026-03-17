package com.sas.platform.domain.repositories;

import java.util.Optional;
import java.util.UUID;

import com.sas.platform.domain.entities.billing.Invoice;

public interface InvoiceRepository {
    Optional<Invoice> findById(UUID id);
    void save(Invoice invoice);
    void delete(UUID id);
}
