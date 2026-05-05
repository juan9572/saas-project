package com.sas.platform.infrastructure.persistence.jpa.entities;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

import com.sas.platform.domain.entities.billing.InvoiceStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "invoice")
public class InvoiceJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID subscriptionId;

    private BigDecimal amount;
    private String currency;

    @Enumerated(EnumType.STRING)
    private InvoiceStatus status;

    private ZonedDateTime periodStart;
    private ZonedDateTime periodEnd;
    
    private ZonedDateTime dueDate;
    private ZonedDateTime paidAt;

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public UUID getSubscriptionId() {
        return subscriptionId;
    }
    public void setSubscriptionId(UUID subscriptionId) {
        this.subscriptionId = subscriptionId;
    }
    public BigDecimal getAmount() {
        return amount;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    public String getCurrency() {
        return currency;
    }
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    public InvoiceStatus getStatus() {
        return status;
    }
    public void setStatus(InvoiceStatus status) {
        this.status = status;
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
    public ZonedDateTime getDueDate() {
        return dueDate;
    }
    public void setDueDate(ZonedDateTime dueDate) {
        this.dueDate = dueDate;
    }
    public ZonedDateTime getPaidAt() {
        return paidAt;
    }
    public void setPaidAt(ZonedDateTime paidAt) {
        this.paidAt = paidAt;
    }
}
