package nl.optiperf.microservices.core.balance.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Objects;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Embeddable
public class BalanceDetails {

    @NotNull(message = "Currency cannot be null.")
    @NotEmpty(message = "Currency cannot be empty.")
    private String currency;

    @NotNull(message = "Current balance cannot be null.")
    private BigDecimal currentBalance;

    @NotNull(message = "Available balance cannot be null.")
    private BigDecimal availableBalance;

    @NotNull(message = "Last transaction date cannot be null.")
    private OffsetDateTime lastTransactionDate;

    public BalanceDetails() {
    }

    public BalanceDetails(String currency, BigDecimal currentBalance, BigDecimal availableBalance, OffsetDateTime lastTransactionDate) {
        this.currency = currency;
        this.currentBalance = currentBalance;
        this.availableBalance = availableBalance;
        this.lastTransactionDate = lastTransactionDate;
    }

    // Getters and Setters
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    public BigDecimal getCurrentBalance() { return currentBalance; }
    public void setCurrentBalance(BigDecimal currentBalance) { this.currentBalance = currentBalance; }
    public BigDecimal getAvailableBalance() { return availableBalance; }
    public void setAvailableBalance(BigDecimal availableBalance) { this.availableBalance = availableBalance; }
    public OffsetDateTime getLastTransactionDate() { return lastTransactionDate; }
    public void setLastTransactionDate(OffsetDateTime lastTransactionDate) { this.lastTransactionDate = lastTransactionDate; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BalanceDetails that = (BalanceDetails) o;
        return Objects.equals(currency, that.currency) &&
               Objects.equals(currentBalance, that.currentBalance) &&
               Objects.equals(availableBalance, that.availableBalance) &&
               Objects.equals(lastTransactionDate, that.lastTransactionDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currency, currentBalance, availableBalance, lastTransactionDate);
    }
}