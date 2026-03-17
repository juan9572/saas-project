package com.sas.platform.infrastructure.persistence.jpa.mappers;

import java.util.Currency;

import org.springframework.stereotype.Component;

import com.sas.platform.domain.entities.subscription.Subscription;
import com.sas.platform.domain.value_objects.BillingPeriod.BillingPeriod;
import com.sas.platform.domain.value_objects.Money.Money;
import com.sas.platform.infrastructure.persistence.jpa.entities.SubscriptionJpaEntity;

@Component
public class SubscriptionMapper {
    public Subscription toDomain(SubscriptionJpaEntity e) {
        BillingPeriod period = new BillingPeriod(e.getPeriodStart(), e.getPeriodEnd());
        Currency currency = Currency.getInstance(e.getRecurringCurrency());
        Money money = new Money(e.getRecurringAmount(), currency);

        return Subscription.rehydrate(
            e.getId(),
            e.getUserId(),
            e.getPlanId(),
            e.getStatus(),
            e.getStartDate(),
            period,
            e.getNextBillingDate(),
            e.getBillingCycle(),
            money,
            e.getGracePeriodEndsAt(),
            e.isCancelAtPeriodEnd(),
            e.getCancelledDate()
        );
    }

    public SubscriptionJpaEntity toJpa(Subscription s) {
        SubscriptionJpaEntity e = new SubscriptionJpaEntity();

        e.setId(s.getId());
        e.setUserId(s.getUserId());
        e.setPlanId(s.getPlanId());
        e.setStatus(s.getStatus());
        e.setNextBillingDate(s.getNextBillingDate());
        e.setGracePeriodEndsAt(s.getGracePeriodEndsAt());
        e.setStartDate(s.getStartDate());
        e.setCancelledDate(s.getCancelledDate());
        e.setCancelAtPeriodEnd(s.getCancelAtPeriodEnd());

        e.setBillingCycle(s.getBillingCycle());

        e.setRecurringAmount(s.getRecurringPrice().getAmount());
        e.setRecurringCurrency(s.getRecurringPrice().getCurrency().getCurrencyCode());

        e.setPeriodStart(s.getCurrentPeriod().getStart());
        e.setPeriodEnd(s.getCurrentPeriod().getEnd());

        return e;
    }
}
