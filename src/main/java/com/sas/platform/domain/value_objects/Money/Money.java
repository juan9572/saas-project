package com.sas.platform.domain.value_objects.Money;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.Objects;

public class Money {
    private final BigDecimal amount;
    private final Currency currency;

    public Money(BigDecimal amount, Currency currency) {
        if (amount == null || currency  == null) {
            throw new IllegalArgumentException("Not valid state for definition of money");
        }

        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Negative money not allowed");
        }

        this.amount = amount.setScale(2, RoundingMode.HALF_UP);
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public Currency getCurrency() {
        return this.currency;
    }

    public Money add(Money other) {
        assertSameCurrency(other);
        return new Money(this.amount.add(other.amount), currency);
    }

    public boolean gte(Money other) {
        assertSameCurrency(other);
        return this.amount.compareTo(other.amount) >= 0;
    }

    private void assertSameCurrency(Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new IllegalStateException("Different currencies cannot be used");
        }
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } 
    
        if (!(object instanceof Money)) {
            return false;
        } 

        Money other = (Money) object;

        return this.amount.equals(other.amount) && this.currency.equals(other.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.amount, this.currency);
    }
}
