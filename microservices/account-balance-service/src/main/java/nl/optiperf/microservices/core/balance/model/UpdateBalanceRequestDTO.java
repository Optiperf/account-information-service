package nl.optiperf.microservices.core.balance.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Objects;

public class UpdateBalanceRequestDTO {

    @NotNull(message = "Currency cannot be null.")
    @NotEmpty(message = "Currency cannot be empty.")
    private String currency;

    @NotNull(message = "Current balance cannot be null.")
    private BigDecimal currentBalance;

    @NotNull(message = "Available balance cannot be null.")
    private BigDecimal availableBalance;

    private OffsetDateTime lastTransactionDate;

    // No-arg constructor
    public UpdateBalanceRequestDTO() {
    }

    // All-args constructor
    public UpdateBalanceRequestDTO(String currency, BigDecimal currentBalance, BigDecimal availableBalance, OffsetDateTime lastTransactionDate) {
        this.currency = currency;
        this.currentBalance = currentBalance;
        this.availableBalance = availableBalance;
        this.lastTransactionDate = lastTransactionDate;
    }

    // Getters
    public String getCurrency() { return currency; }
    public BigDecimal getCurrentBalance() { return currentBalance; }
    public BigDecimal getAvailableBalance() { return availableBalance; }
    public OffsetDateTime getLastTransactionDate() { return lastTransactionDate; }

    // Setters (optional, but good practice for DTOs if needed for deserialization)
    public void setCurrency(String currency) { this.currency = currency; }
    public void setCurrentBalance(BigDecimal currentBalance) { this.currentBalance = currentBalance; }
    public void setAvailableBalance(BigDecimal availableBalance) { this.availableBalance = availableBalance; }
    public void setLastTransactionDate(OffsetDateTime lastTransactionDate) { this.lastTransactionDate = lastTransactionDate; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateBalanceRequestDTO that = (UpdateBalanceRequestDTO) o;
        return Objects.equals(currency, that.currency) &&
               Objects.equals(currentBalance, that.currentBalance) &&
               Objects.equals(availableBalance, that.availableBalance) &&
               Objects.equals(lastTransactionDate, that.lastTransactionDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currency, currentBalance, availableBalance, lastTransactionDate);
    }

    // toString is often useful for logging/debugging, but omitted for brevity here.
}
