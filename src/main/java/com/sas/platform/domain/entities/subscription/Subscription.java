package com.sas.platform.domain.entities.subscription;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.sas.platform.domain.entities.plan.BillingCycle;
import com.sas.platform.domain.events.DomainEvent;
import com.sas.platform.domain.events.GracePeriodEnteredEvent;
import com.sas.platform.domain.events.GracePeriodExpiredEvent;
import com.sas.platform.domain.events.SubscriptionCancelledEvent;
import com.sas.platform.domain.value_objects.BillingPeriod.BillingPeriod;
import com.sas.platform.domain.value_objects.Money.Money;

public class Subscription {
    private UUID id;
    private UUID userId;
    private UUID planId;
    private SubscriptionStatus status;
    private ZonedDateTime nextBillingDate;
    private BillingCycle billingCycle;
    private Money recurringPrice;
    private ZonedDateTime gracePeriodEndsAt;
    private ZonedDateTime startDate;
    private BillingPeriod currentPeriod;
    private boolean cancelAtPeriodEnd;
    private ZonedDateTime cancelledDate;
    private List<DomainEvent> domainEvents = new ArrayList<>();

    public Subscription(
        UUID id,
        UUID userId,
        UUID planId,
        ZonedDateTime startDate,
        BillingPeriod currentPeriod,
        ZonedDateTime nextBillingDate,
        BillingCycle billingCycle,
        Money recurringPrice
    ) {
        this.id = id;
        this.userId = userId;
        this.planId = planId;
        this.status = SubscriptionStatus.ACTIVE;
        this.nextBillingDate = nextBillingDate;
        this.billingCycle = billingCycle;
        this.recurringPrice = recurringPrice;
        this.startDate = startDate;
        this.currentPeriod = currentPeriod;
        this.cancelAtPeriodEnd = false;
    }

    public static Subscription rehydrate(
            UUID id,
            UUID userId,
            UUID planId,
            SubscriptionStatus status,
            ZonedDateTime startDate,
            BillingPeriod period,
            ZonedDateTime nextBillingDate,
            BillingCycle billingCycle,
            Money recurringPrice,
            ZonedDateTime gracePeriodEndsAt,
            boolean cancelAtPeriodEnd,
            ZonedDateTime cancelledDate
    ) {
        Subscription s =
                new Subscription(
                        id,
                        userId,
                        planId,
                        startDate,
                        period,
                        nextBillingDate,
                        billingCycle,
                        recurringPrice
                );

        s.status = status;
        s.gracePeriodEndsAt = gracePeriodEndsAt;
        s.cancelAtPeriodEnd = cancelAtPeriodEnd;
        s.cancelledDate = cancelledDate;

        return s;
    }

    public UUID getId() {
        return this.id;
    }

    public UUID getUserId() {
        return this.userId;
    }

    public UUID getPlanId() {
        return this.planId;
    }

    public SubscriptionStatus getStatus() {
        return this.status;
    }

    public ZonedDateTime getStartDate() {
        return this.startDate;
    }

    public ZonedDateTime getNextBillingDate() {
        return this.nextBillingDate;
    }

    public ZonedDateTime getCancelledDate() {
        return this.cancelledDate;
    }

    public ZonedDateTime getGracePeriodEndsAt() {
        return this.gracePeriodEndsAt;
    }

    public BillingPeriod getCurrentPeriod() {
        return this.currentPeriod;
    }

    public boolean getCancelAtPeriodEnd() {
        return this.cancelAtPeriodEnd;
    }

    public BillingCycle getBillingCycle() {
        return this.billingCycle;
    }

    public Money getRecurringPrice() {
        return this.recurringPrice;
    }

    public List<DomainEvent> pullDomainEvents() {
        List<DomainEvent> events = new ArrayList<>(this.domainEvents);
        this.domainEvents.clear();
        return events;
    }

    public void scheduleCancelAtPeriodEnd() {
        if (
            !this.status.equals(SubscriptionStatus.ACTIVE) &&
            !this.status.equals(SubscriptionStatus.PAST_DUE)
        ) {
            throw new IllegalStateException("Subscription already termiated");
        }
        
        this.cancelAtPeriodEnd = true;
    }

    public void cancelNow() {
        if (
            this.status.equals(SubscriptionStatus.CANCELLED) ||
            this.status.equals(SubscriptionStatus.EXPIRED)
        ) {
            throw new IllegalStateException("Subscription already termiated");
        }
        
        this.status = SubscriptionStatus.CANCELLED;
        this.cancelledDate = ZonedDateTime.now();
        this.cancelAtPeriodEnd = false;

        domainEvents.add(
            new SubscriptionCancelledEvent(
                this.id,
                ZonedDateTime.now()
            )
        );
    }

    public void reactivateFromPastDue() {
        if (!this.status.equals(SubscriptionStatus.PAST_DUE)) {
            throw new IllegalStateException("Only past due subscriptions can be reactivated");
        }

        this.status = SubscriptionStatus.ACTIVE;
        this.gracePeriodEndsAt = null;
    }

    public void enterGracePeriod(ZonedDateTime graceEnd) {
        if (graceEnd == null) {
            throw new IllegalStateException("Not valid value for graceEnd");
        }

        if (this.gracePeriodEndsAt != null) {
            throw new IllegalStateException("Subscription already in grace period");
        }

        if (!this.status.equals(SubscriptionStatus.ACTIVE)) {
            throw new IllegalStateException("Only active subscriptions can enter grace");
        }

        this.status = SubscriptionStatus.PAST_DUE;
        this.gracePeriodEndsAt = graceEnd;

        domainEvents.add(
            new GracePeriodEnteredEvent(
                this.id,
                graceEnd,
                ZonedDateTime.now()
            )
        );
    }

    public void expireIfGracePeriodElapsed(ZonedDateTime now) {
        if (!this.status.equals(SubscriptionStatus.PAST_DUE)) {
            throw new IllegalStateException("Only PAST DUE subscriptions can be expired");
        }

        if (this.gracePeriodEndsAt == null) {
            throw new IllegalStateException("Subscription not in grace period");
        }

        if (now.isAfter(this.gracePeriodEndsAt)) {
            this.status = SubscriptionStatus.EXPIRED;
            domainEvents.add(
                new GracePeriodExpiredEvent(
                    this.id,
                    now
                )
            );
        }
    }

    public void advanceToNextBillingPeriod() {
        if (
            this.status.equals(SubscriptionStatus.CANCELLED) ||
            this.status.equals(SubscriptionStatus.EXPIRED)
        ) {
            throw new IllegalStateException("Cannot advance terminated subscription");
        }

        ZonedDateTime start = this.currentPeriod.getEnd();
        ZonedDateTime end;

        switch (this.billingCycle) {
            case MONTHLY:
                end = start.plusMonths(1);
                break;
            case YEARLY:
                end = start.plusYears(1);
                break;
            default:
                throw new IllegalStateException("Unsupported billing cycle");
        }

        this.currentPeriod = new BillingPeriod(start, end);
        this.nextBillingDate = start;

        if (this.cancelAtPeriodEnd) {
            this.cancelNow();
        }
    }
}
