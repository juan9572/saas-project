package com.sas.platform.domain.entities.billing;

import java.time.ZonedDateTime;
import java.util.UUID;

import com.sas.platform.domain.value_objects.BillingPeriod.BillingPeriod;
import com.sas.platform.domain.value_objects.Money.Money;

public class Invoice {
    private UUID id;
    private UUID subscriptionId;
    private Money amount;
    private InvoiceStatus status;
    private BillingPeriod period;
    private ZonedDateTime dueDate;
    private ZonedDateTime paidAt;

    public Invoice(
        UUID id,
        UUID subscriptionId,
        Money amount,
        BillingPeriod period,
        ZonedDateTime dueDate
    ) {
        this.id = id;
        this.subscriptionId = subscriptionId;
        this.amount = amount;
        this.period = period;
        this.dueDate = dueDate;
        this.status = InvoiceStatus.OPEN;
    }

    public static Invoice rehydrate(
        UUID id,
        UUID subscriptionId,
        Money amount,
        InvoiceStatus status,
        BillingPeriod period,
        ZonedDateTime dueDate,
        ZonedDateTime paidAt
    ) {
        Invoice i = 
            new Invoice(
                id,
                subscriptionId,
                amount,
                period,
                dueDate
            );

        i.status = status;
        i.paidAt = paidAt;

        return i;
    }

    public UUID getId() {
        return this.id;
    }

    public UUID getSubscriptionId() {
        return this.subscriptionId;
    }

    public Money getAmount() {
        return this.amount;
    }

    public InvoiceStatus getStatus() {
        return this.status;
    }

    public BillingPeriod getPeriod() {
        return this.period;
    }

    public ZonedDateTime getDueDate() {
        return this.dueDate;
    }

    public ZonedDateTime getPaidAt() {
        return this.paidAt;
    }

    public void markPaid() {
        if (
            !this.status.equals(InvoiceStatus.OPEN) &&
            !this.status.equals(InvoiceStatus.PAST_DUE)
        ) {
            throw new IllegalStateException("Invoice cannot be paid");
        }

        this.status = InvoiceStatus.PAID;
        this.paidAt = ZonedDateTime.now();
    }

    public void markPastDue() {
        if (!this.status.equals(InvoiceStatus.OPEN)) {
            throw new IllegalStateException("Only open invoices can become past due");
        }

        this.status = InvoiceStatus.PAST_DUE;
    }

    public void voidInvoice() {
        if (this.status.equals(InvoiceStatus.PAID)) {
            throw new IllegalStateException("Cannot void paid invoice");
        }

        this.status = InvoiceStatus.VOID;
    }

    public void checkIfPastDue(ZonedDateTime now) {
        if (!this.status.equals(InvoiceStatus.OPEN)) {
            return; // Invoice is still open
        }

        if (now.isAfter(this.dueDate)) {
            this.status = InvoiceStatus.PAST_DUE;
        }
    }
}
