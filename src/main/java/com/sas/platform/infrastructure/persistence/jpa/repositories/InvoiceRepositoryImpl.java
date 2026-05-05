package com.sas.platform.infrastructure.persistence.jpa.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.sas.platform.domain.entities.billing.Invoice;
import com.sas.platform.domain.repositories.InvoiceRepository;
import com.sas.platform.infrastructure.persistence.jpa.entities.InvoiceJpaEntity;
import com.sas.platform.infrastructure.persistence.jpa.mappers.InvoiceMapper;
import com.sas.platform.infrastructure.persistence.jpa.springdata.SpringDataInvoiceJpaRepository;

@Repository
public class InvoiceRepositoryImpl implements InvoiceRepository {
    private final SpringDataInvoiceJpaRepository jpaRepo;
    private final InvoiceMapper mapper;

    public InvoiceRepositoryImpl(
            SpringDataInvoiceJpaRepository jpaRepo,
            InvoiceMapper mapper
    ) {
        this.jpaRepo = jpaRepo;
        this.mapper = mapper;
    }

    @Override
    public Optional<Invoice> findById(UUID id) {
        return jpaRepo.findById(id).map(mapper::toDomain);
    }

    @Override
    public void save(Invoice invoice) {
        InvoiceJpaEntity entity = mapper.toJpa(invoice);
        jpaRepo.save(entity);
    }

    @Override
    public void delete(UUID id) {
        jpaRepo.deleteById(id);
    }
}
