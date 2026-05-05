package com.sas.platform.infrastructure.persistence.jpa.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.sas.platform.domain.entities.payment.Payment;
import com.sas.platform.domain.repositories.PaymentRepository;
import com.sas.platform.infrastructure.persistence.jpa.entities.PaymentJpaEntity;
import com.sas.platform.infrastructure.persistence.jpa.mappers.PaymentMapper;
import com.sas.platform.infrastructure.persistence.jpa.springdata.SpringDataPaymentJpaRepository;

@Repository
public class PaymentRepositoryImpl implements PaymentRepository {
    private final SpringDataPaymentJpaRepository jpaRepo;
    private final PaymentMapper mapper;

    public PaymentRepositoryImpl(
            SpringDataPaymentJpaRepository jpaRepo,
            PaymentMapper mapper
    ) {
        this.jpaRepo = jpaRepo;
        this.mapper = mapper;
    }

    @Override
    public Optional<Payment> findById(UUID id) {
        return jpaRepo.findById(id).map(mapper::toDomain);
    }

    @Override
    public void save(Payment payment) {
        PaymentJpaEntity entity = mapper.toJpa(payment);
        jpaRepo.save(entity);
    }

    @Override
    public void delete(UUID id) {
        jpaRepo.deleteById(id);
    }
}
