package nl.optiperf.microservices.core.balance.model;

import java.math.BigDecimal;
import java.util.Objects;

public class LimitedBalanceDetailsDTO {
    private String currency;
    private BigDecimal currentBalance;
    private BigDecimal availableBalance;

    // No-arg constructor
    public LimitedBalanceDetailsDTO() {
    }

    // All-args constructor
    public LimitedBalanceDetailsDTO(String currency, BigDecimal currentBalance, BigDecimal availableBalance) {
        this.currency = currency;
        this.currentBalance = currentBalance;
        this.availableBalance = availableBalance;
    }

    // Getters
    public String getCurrency() { return currency; }
    public BigDecimal getCurrentBalance() { return currentBalance; }
    public BigDecimal getAvailableBalance() { return availableBalance; }

    // Setters
    public void setCurrency(String currency) { this.currency = currency; }
    public void setCurrentBalance(BigDecimal currentBalance) { this.currentBalance = currentBalance; }
    public void setAvailableBalance(BigDecimal availableBalance) { this.availableBalance = availableBalance; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LimitedBalanceDetailsDTO that = (LimitedBalanceDetailsDTO) o;
        return Objects.equals(currency, that.currency) &&
               Objects.equals(currentBalance, that.currentBalance) &&
               Objects.equals(availableBalance, that.availableBalance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currency, currentBalance, availableBalance);
    }

    @Override
    public String toString() {
        return "LimitedBalanceDetailsDTO{" +
               "currency='" + currency + '\'' +
               ", currentBalance=" + currentBalance +
               ", availableBalance=" + availableBalance +
               '}';
    }
}