package nl.optiperf.microservices.core.balance.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

public class UpdateBalanceRequestDTO {

    @NotNull(message = "Currency cannot be null.")
    @NotEmpty(message = "Currency cannot be empty.")
    private String currency;

    @NotNull(message = "Current balance cannot be null.")
    private BigDecimal currentBalance;

    @NotNull(message = "Available balance cannot be null.")
    private BigDecimal availableBalance;

    // No-arg constructor
    public UpdateBalanceRequestDTO() {
    }

    // All-args constructor
    public UpdateBalanceRequestDTO(String currency, BigDecimal currentBalance, BigDecimal availableBalance) {
        this.currency = currency;
        this.currentBalance = currentBalance;
        this.availableBalance = availableBalance;
    }

    // Getters
    public String getCurrency() { return currency; }
    public BigDecimal getCurrentBalance() { return currentBalance; }
    public BigDecimal getAvailableBalance() { return availableBalance; }

    // Setters (optional, but good practice for DTOs if needed for deserialization)
    public void setCurrency(String currency) { this.currency = currency; }
    public void setCurrentBalance(BigDecimal currentBalance) { this.currentBalance = currentBalance; }
    public void setAvailableBalance(BigDecimal availableBalance) { this.availableBalance = availableBalance; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateBalanceRequestDTO that = (UpdateBalanceRequestDTO) o;
        return Objects.equals(currency, that.currency) &&
               Objects.equals(currentBalance, that.currentBalance) &&
               Objects.equals(availableBalance, that.availableBalance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currency, currentBalance, availableBalance);
    }

    // toString is often useful for logging/debugging, but omitted for brevity here.
}
