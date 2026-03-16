package com.sas.platform.domain.entities.payment;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.sas.platform.domain.events.DomainEvent;
import com.sas.platform.domain.events.PaymentFailedEvent;
import com.sas.platform.domain.events.PaymentSucceededEvent;
import com.sas.platform.domain.value_objects.Money.Money;

public class Payment {
    private UUID id;
    private UUID invoiceId;
    private UUID paymentMethodId;
    private UUID providerTransactionId;
    private Money amount;
    private int attempNumber;
    private PaymentStatus status;
    private ZonedDateTime processedAt;
    private List<DomainEvent> domainEvents = new ArrayList<>();

    public Payment(UUID id, UUID invoiceId, UUID paymentMethodId, Money amount, int attempNumber) {
        this.id = id;
        this.invoiceId = invoiceId;
        this.paymentMethodId = paymentMethodId;
        this.amount = amount;
        this.attempNumber = attempNumber;
        this.status = PaymentStatus.PENDING;
    }

    public UUID getId() {
        return this.id;
    }

    public UUID getInvoiceId() {
        return this.invoiceId;
    }

    public UUID getPaymentMethodId() {
        return this.paymentMethodId;
    }
    
    public UUID getProviderTransactionId() {
        return this.providerTransactionId;
    }

    public Money getAmount() {
        return this.amount;
    }

    public PaymentStatus getStatus() {
        return this.status;
    }

    public ZonedDateTime getProcessedAt() {
        return this.processedAt;
    }

    public int getAttempNumber() {
        return this.attempNumber;
    }

    public List<DomainEvent> pullDomainEvents() {
        List<DomainEvent> events = new ArrayList<>(this.domainEvents);
        this.domainEvents.clear();
        return events;
    }

    public void attachProviderTransaction(UUID providerTxnId) {
        if (!this.status.equals(PaymentStatus.PENDING)) {
            throw new IllegalStateException("Payment already processed");
        }

        if (this.providerTransactionId != null) {
            throw new IllegalStateException("Payment already processed");
        }

        this.providerTransactionId = providerTxnId;
    }

    public void markFailed() {
        if (!this.status.equals(PaymentStatus.PENDING)) {
            throw new IllegalStateException("Payment already processed");
        }

        this.status = PaymentStatus.FAILED;
        this.processedAt = ZonedDateTime.now();
        domainEvents.add(
            new PaymentFailedEvent(
                this.invoiceId,
                this.attempNumber,
                this.processedAt
            )
        );
    }

    public void markSuccess() {
        if (!this.status.equals(PaymentStatus.PENDING)) {
            throw new IllegalStateException("Payment already processed");
        }

        this.status = PaymentStatus.SUCCESS;
        this.processedAt = ZonedDateTime.now();
        domainEvents.add(
            new PaymentSucceededEvent(
                this.id,
                this.invoiceId,
                this.processedAt
            )
        );
    }
}
