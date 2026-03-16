package com.sas.platform.domain.entities.plan;

import java.time.ZonedDateTime;
import java.util.UUID;

import com.sas.platform.domain.value_objects.Money.Money;

public class Plan {
    private UUID id;
    private String name;
    private Money price;
    private BillingCycle billingCycle;
    private String description;
    private PlanStatuses status;
    private ZonedDateTime updateDate;
    private ZonedDateTime createDate;

    public Plan(
        UUID id,
        String name,
        Money price,
        BillingCycle billingCycle,
        String description,
        PlanStatuses status,
        ZonedDateTime updateDate,
        ZonedDateTime createDate
    ) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.billingCycle = billingCycle;
        this.description = description;
        this.status = status;
        this.updateDate = updateDate;
        this.createDate = createDate;
    }

    public UUID getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Money getPrice() {
        return this.price;
    }

    public BillingCycle getBillingCycle() {
        return this.billingCycle;
    }

    public String getDescription() {
        return this.description;
    }

    public PlanStatuses getStatus() {
        return this.status;
    }

    public ZonedDateTime getUpdateDate() {
        return this.updateDate;
    }

    public ZonedDateTime getCreateDate() {
        return this.createDate;
    }

    public void deactivate() {
        if (!this.status.equals(PlanStatuses.INACTIVE)) {
            throw new IllegalStateException("Plan already inactive");
        }

        this.status = PlanStatuses.INACTIVE;
        this.updateDate = ZonedDateTime.now();
    }

    public void activate() {
        if (!this.status.equals(PlanStatuses.ACTIVE)) {
            throw new IllegalStateException("Plan already active");
        }

        this.status = PlanStatuses.ACTIVE;
        this.updateDate = ZonedDateTime.now();
    }
}
