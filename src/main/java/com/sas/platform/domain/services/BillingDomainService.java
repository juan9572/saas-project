package com.sas.platform.domain.services;

import java.time.ZonedDateTime;

import com.sas.platform.domain.entities.billing.Invoice;
import com.sas.platform.domain.entities.payment.Payment;
import com.sas.platform.domain.entities.payment.PaymentMethod;
import com.sas.platform.domain.entities.subscription.Subscription;

public interface BillingDomainService {
    Payment attemptPayment(
        Invoice invoice,
        Subscription subscription,
        PaymentMethod method,
        int attemptNumber
    );
    
    void handlePaymentSuccess(Invoice invoice, Subscription subscription);

    void handleInvoicePastDue(
        Invoice invoice,
        Subscription subscription,
        int graceDays
    );

    Invoice createInvoiceForSubscription(
        Subscription sub,
        ZonedDateTime dueDate
    );

    void rollOverPeriod(Subscription subscription, ZonedDateTime now);
}
