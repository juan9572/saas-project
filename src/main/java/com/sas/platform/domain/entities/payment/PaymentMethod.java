package com.sas.platform.domain.entities.payment;

import java.time.ZonedDateTime;
import java.util.UUID;

public class PaymentMethod {
    private UUID id;
    private UUID userId;
    private PaymentProvider provider;
    private String externalReference;
    private PaymentMethodStatus status;
    private boolean isDefault; //TODO Falta lógica para orquestar solo 1 metodo como default
    private ZonedDateTime deactivatedAt;
    private ZonedDateTime createDate;

    public PaymentMethod(UUID id, UUID userId, PaymentProvider provider, String externalReference, boolean isDefault) {
        this.id = id;
        this.userId = userId;
        this.provider = provider;
        this.externalReference = externalReference;
        this.isDefault = isDefault;
        this.status = PaymentMethodStatus.ACTIVE;
        this.createDate = ZonedDateTime.now();
    }

    public UUID getId() {
        return this.id;
    }

    public UUID getUserId() {
        return this.userId;
    }

    public PaymentProvider getProvider() {
        return this.provider;
    }

    public String getExternalReference() {
        return this.externalReference;
    }

    public PaymentMethodStatus getStatus() {
        return this.status;
    }


    public ZonedDateTime getDeactivatedAt() {
        return this.deactivatedAt;
    }

    public ZonedDateTime getCreateDate() {
        return this.createDate;
    }

    public void deactivate() {
        if (this.status.equals(PaymentMethodStatus.INACTIVE)) {
            throw new IllegalStateException("Payment method already inactive");
        }

        this.status = PaymentMethodStatus.INACTIVE;
        this.deactivatedAt = ZonedDateTime.now();
    }

    public void active() {
        if (this.status.equals(PaymentMethodStatus.ACTIVE)) {
            throw new IllegalStateException("Payment method already active");
        }

        this.status = PaymentMethodStatus.ACTIVE;
        this.deactivatedAt = null;
    }


    public void markAsDefault() {
        if (!this.status.equals(PaymentMethodStatus.ACTIVE)) {
            throw new IllegalStateException("Inactive method cannot be default");
        }

        this.isDefault = true;
    }

    public void removeDefault() {
        this.isDefault = false;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public boolean isUsable() {
        return this.status.equals(PaymentMethodStatus.ACTIVE);
    }
}
