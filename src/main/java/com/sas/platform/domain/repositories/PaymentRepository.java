package com.sas.platform.domain.repositories;

import java.util.Optional;
import java.util.UUID;

import com.sas.platform.domain.entities.payment.Payment;

public interface PaymentRepository {
    Optional<Payment> findById(UUID id);
    void save(Payment payment);
    void delete(UUID id);
}
