package nl.optiperf.microservices.core.balance.model;

import java.time.OffsetDateTime;
import java.util.Objects;

public class BalanceDetails {

    private Double currentBalance;
    private Double availableBalance;
    private OffsetDateTime lastTransactionDate;

    // Constructors
    public BalanceDetails() {}

    public BalanceDetails(Double currentBalance, Double availableBalance, OffsetDateTime lastTransactionDate) {
        this.currentBalance = currentBalance;
        this.availableBalance = availableBalance;
        this.lastTransactionDate = lastTransactionDate;
    }

    // Getters and Setters
    public Double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(Double currentBalance) {
        this.currentBalance = currentBalance;
    }

    public Double getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(Double availableBalance) {
        this.availableBalance = availableBalance;
    }

    public OffsetDateTime getLastTransactionDate() {
        return lastTransactionDate;
    }

    public void setLastTransactionDate(OffsetDateTime lastTransactionDate) {
        this.lastTransactionDate = lastTransactionDate;
    }

    @Override
    public String toString() {
        return "BalanceDetails{" +
                "currentBalance=" + currentBalance +
                ", availableBalance=" + availableBalance +
                ", lastTransactionDate=" + lastTransactionDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BalanceDetails that = (BalanceDetails) o;
        return Objects.equals(currentBalance, that.currentBalance) &&
               Objects.equals(availableBalance, that.availableBalance) &&
               Objects.equals(lastTransactionDate, that.lastTransactionDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentBalance, availableBalance, lastTransactionDate);
    }
}