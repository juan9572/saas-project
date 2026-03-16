package com.sas.platform.domain.services;

import java.time.ZonedDateTime;
import java.util.UUID;

import com.sas.platform.domain.entities.billing.Invoice;
import com.sas.platform.domain.entities.billing.InvoiceStatus;
import com.sas.platform.domain.entities.payment.Payment;
import com.sas.platform.domain.entities.payment.PaymentMethod;
import com.sas.platform.domain.entities.subscription.Subscription;
import com.sas.platform.domain.entities.subscription.SubscriptionStatus;

public class DefaultBillingDomainService implements BillingDomainService {
    @Override
    public Payment attemptPayment(
        Invoice invoice,
        Subscription subscription,
        PaymentMethod method,
        int attemptNumber
    ) {
        if (!method.isUsable()) {
            throw new IllegalStateException("Payment method unusable");
        }

        if (
            !invoice.getStatus().equals(InvoiceStatus.OPEN) &&
            !invoice.getStatus().equals(InvoiceStatus.PAST_DUE)
        ) {
                throw new IllegalStateException("Invoice not collectible");
        }

        return new Payment(
            UUID.randomUUID(),
            invoice.getId(),
            method.getId(),
            invoice.getAmount(),
            attemptNumber
        );
    }

    @Override
    public void handlePaymentSuccess(Invoice invoice, Subscription subscription) {
        invoice.markPaid();

        if (subscription.getStatus().equals(SubscriptionStatus.PAST_DUE)) {
            subscription.reactivateFromPastDue();
        }
    }

    @Override
    public void handleInvoicePastDue(
        Invoice invoice,
        Subscription subscription,
        int graceDays
    ) {
        invoice.markPastDue();

        subscription.enterGracePeriod(
            ZonedDateTime.now().plusDays(graceDays)
        );
    }

    @Override
    public Invoice createInvoiceForSubscription(Subscription sub, ZonedDateTime dueDate) {
        if (
            !sub.getStatus().equals(SubscriptionStatus.ACTIVE) &&
            !sub.getStatus().equals(SubscriptionStatus.PAST_DUE)
        ) {
            throw new IllegalStateException("Cannot invoice inactive subscription");
        }

        return new Invoice(
            UUID.randomUUID(),
            sub.getId(),
            sub.getRecurringPrice(),
            sub.getCurrentPeriod(),
            dueDate
        );
    }

    @Override
    public void rollOverPeriod(Subscription subscription, ZonedDateTime now) {
        if (
            subscription.getStatus().equals(SubscriptionStatus.CANCELLED) ||
            subscription.getStatus().equals(SubscriptionStatus.EXPIRED)
        ) {
            return;
        }

        while (!now.isBefore(subscription.getNextBillingDate())) {
            subscription.advanceToNextBillingPeriod();
        }
    }
}
