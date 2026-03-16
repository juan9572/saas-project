package com.sas.platform.domain.value_objects.BillingPeriod;

import java.time.ZonedDateTime;
import java.util.Objects;

public class BillingPeriod {
    private final ZonedDateTime start;
    private final ZonedDateTime end;

    public BillingPeriod(ZonedDateTime start, ZonedDateTime end) {
        if (end.isBefore(start)) {
            throw new IllegalArgumentException("End date can't be before start date");
        }

        this.start = start;
        this.end = end;
    }

    public ZonedDateTime getStart() {
        return this.start;
    }

    public ZonedDateTime getEnd() {
        return this.end;
    }

    public boolean contains(ZonedDateTime date) {
        return !date.isBefore(start) && date.isBefore(end);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } 
    
        if (!(object instanceof BillingPeriod)) {
            return false;
        } 

        BillingPeriod other = (BillingPeriod) object;

        return this.start.equals(other.start) && this.end.equals(other.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.start, this.end);
    }
}
