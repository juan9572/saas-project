package com.sas.platform.infrastructure.persistence.jpa.mappers;

import java.util.Currency;

import org.springframework.stereotype.Component;

import com.sas.platform.domain.entities.payment.Payment;
import com.sas.platform.domain.value_objects.Money.Money;
import com.sas.platform.infrastructure.persistence.jpa.entities.PaymentJpaEntity;

@Component
public class PaymentMapper {
    public Payment toDomain(PaymentJpaEntity e) {
        Money money = new Money(e.getAmount(), Currency.getInstance(e.getCurrency()));

        return Payment.rehydrate(
            e.getId(),
            e.getInvoiceId(),
            e.getPaymentMethodId(),
            money,
            e.getAttempNumber(),
            e.getStatus(),
            e.getProcessedAt(),
            e.getProviderTransactionId()
        );
    }

    public PaymentJpaEntity toJpa(Payment p) {
        PaymentJpaEntity e = new PaymentJpaEntity();

        e.setId(p.getId());
        e.setInvoiceId(p.getInvoiceId());
        e.setPaymentMethodId(p.getPaymentMethodId());
        e.setAmount(p.getAmount().getAmount());
        e.setCurrency(p.getAmount().getCurrency().getCurrencyCode());
        e.setAttempNumber(p.getAttempNumber());
        e.setStatus(p.getStatus());
        e.setProcessedAt(p.getProcessedAt());
        e.setProviderTransactionId(p.getProviderTransactionId());

        return e;
    }
}
