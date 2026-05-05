package com.sas.platform.infrastructure.persistence.jpa.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.sas.platform.domain.entities.subscription.Subscription;
import com.sas.platform.domain.repositories.SubscriptionRepository;
import com.sas.platform.infrastructure.persistence.jpa.entities.SubscriptionJpaEntity;
import com.sas.platform.infrastructure.persistence.jpa.mappers.SubscriptionMapper;
import com.sas.platform.infrastructure.persistence.jpa.springdata.SpringDataSubscriptionJpaRepository;

@Repository
public class SubscriptionRepositoryImpl implements SubscriptionRepository {
    private final SpringDataSubscriptionJpaRepository jpaRepo;
    private final SubscriptionMapper mapper;

    public SubscriptionRepositoryImpl(
            SpringDataSubscriptionJpaRepository jpaRepo,
            SubscriptionMapper mapper
    ) {
        this.jpaRepo = jpaRepo;
        this.mapper = mapper;
    }

    @Override
    public Optional<Subscription> findById(UUID id) {
        return jpaRepo.findById(id).map(mapper::toDomain);
    }

    @Override
    public void save(Subscription subscription) {
        SubscriptionJpaEntity entity = mapper.toJpa(subscription);
        jpaRepo.save(entity);
    }

    @Override
    public void delete(UUID id) {
        jpaRepo.deleteById(id);
    }
}
