package com.sas.platform.infrastructure.persistence.jpa.mappers;

import java.util.Currency;

import org.springframework.stereotype.Component;

import com.sas.platform.domain.entities.billing.Invoice;
import com.sas.platform.domain.value_objects.BillingPeriod.BillingPeriod;
import com.sas.platform.domain.value_objects.Money.Money;
import com.sas.platform.infrastructure.persistence.jpa.entities.InvoiceJpaEntity;

@Component
public class InvoiceMapper {
    public Invoice toDomain(InvoiceJpaEntity e) {
        Money money = new Money(e.getAmount(), Currency.getInstance(e.getCurrency()));
        BillingPeriod period = new BillingPeriod(e.getPeriodStart(), e.getPeriodEnd());

        return Invoice.rehydrate(
            e.getId(),
            e.getSubscriptionId(),
            money,
            e.getStatus(),
            period,
            e.getDueDate(),
            e.getPaidAt()
        );
    }

    public InvoiceJpaEntity toJpa(Invoice invoice) {
        InvoiceJpaEntity e = new InvoiceJpaEntity();

        e.setId(invoice.getId());
        e.setSubscriptionId(invoice.getSubscriptionId());
        e.setAmount(invoice.getAmount().getAmount());
        e.setCurrency(invoice.getAmount().getCurrency().getCurrencyCode());
        e.setStatus(invoice.getStatus());
        e.setPeriodStart(invoice.getPeriod().getStart());
        e.setPeriodEnd(invoice.getPeriod().getEnd());
        e.setDueDate(invoice.getDueDate());
        e.setPaidAt(invoice.getPaidAt());

        return e;
    }
}
