package com.sas.platform.infrastructure.persistence.jpa.entities;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

import com.sas.platform.domain.entities.plan.BillingCycle;
import com.sas.platform.domain.entities.subscription.SubscriptionStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "subscription")
public class SubscriptionJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID userId;
    private UUID planId;

    @Enumerated(EnumType.STRING)
    private SubscriptionStatus status;

    private ZonedDateTime nextBillingDate;
    private ZonedDateTime gracePeriodEndsAt;
    private ZonedDateTime startDate;
    private ZonedDateTime cancelledDate;

    private boolean cancelAtPeriodEnd;

    private BigDecimal recurringAmount;
    private String recurringCurrency;

    @Enumerated(EnumType.STRING)
    private BillingCycle billingCycle;

    private ZonedDateTime periodStart;
    private ZonedDateTime periodEnd;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getPlanId() {
        return planId;
    }

    public void setPlanId(UUID planId) {
        this.planId = planId;
    }

    public SubscriptionStatus getStatus() {
        return status;
    }

    public void setStatus(SubscriptionStatus status) {
        this.status = status;
    }

    public ZonedDateTime getNextBillingDate() {
        return nextBillingDate;
    }

    public void setNextBillingDate(ZonedDateTime nextBillingDate) {
        this.nextBillingDate = nextBillingDate;
    }

    public ZonedDateTime getGracePeriodEndsAt() {
        return gracePeriodEndsAt;
    }

    public void setGracePeriodEndsAt(ZonedDateTime gracePeriodEndsAt) {
        this.gracePeriodEndsAt = gracePeriodEndsAt;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTime getCancelledDate() {
        return cancelledDate;
    }

    public void setCancelledDate(ZonedDateTime cancelledDate) {
        this.cancelledDate = cancelledDate;
    }

    public boolean isCancelAtPeriodEnd() {
        return cancelAtPeriodEnd;
    }

    public void setCancelAtPeriodEnd(boolean cancelAtPeriodEnd) {
        this.cancelAtPeriodEnd = cancelAtPeriodEnd;
    }

    public BigDecimal getRecurringAmount() {
        return recurringAmount;
    }

    public void setRecurringAmount(BigDecimal recurringAmount) {
        this.recurringAmount = recurringAmount;
    }

    public String getRecurringCurrency() {
        return recurringCurrency;
    }

    public void setRecurringCurrency(String recurringCurrency) {
        this.recurringCurrency = recurringCurrency;
    }

    public BillingCycle getBillingCycle() {
        return billingCycle;
    }

    public void setBillingCycle(BillingCycle billingCycle) {
        this.billingCycle = billingCycle;
    }

    public ZonedDateTime getPeriodStart() {
        return periodStart;
    }

    public void setPeriodStart(ZonedDateTime periodStart) {
        this.periodStart = periodStart;
    }

    public ZonedDateTime getPeriodEnd() {
        return periodEnd;
    }

    public void setPeriodEnd(ZonedDateTime periodEnd) {
        this.periodEnd = periodEnd;
    }
}
