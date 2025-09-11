package nl.optiperf.microservices.core.balance.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;

@Embeddable
public class BalanceDetails {

    @NotNull(message = "Current balance cannot be null.")
    private BigDecimal currentBalance;

    @NotNull(message = "Available balance cannot be null.")
    private BigDecimal availableBalance;

    @NotNull(message = "Last transaction date cannot be null.")
    private OffsetDateTime lastTransactionDate;

    public BalanceDetails() {}

    public BalanceDetails(BigDecimal currentBalance, BigDecimal availableBalance, OffsetDateTime lastTransactionDate) {
        this.currentBalance = currentBalance;
        this.availableBalance = availableBalance;
        this.lastTransactionDate = lastTransactionDate;
    }

    public BigDecimal getCurrentBalance() { return currentBalance; }
    public void setCurrentBalance(BigDecimal currentBalance) { this.currentBalance = currentBalance; }

    public BigDecimal getAvailableBalance() { return availableBalance; }
    public void setAvailableBalance(BigDecimal availableBalance) { this.availableBalance = availableBalance; }

    public OffsetDateTime getLastTransactionDate() { return lastTransactionDate; }
    public void setLastTransactionDate(OffsetDateTime lastTransactionDate) { this.lastTransactionDate = lastTransactionDate; }
}