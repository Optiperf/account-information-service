package nl.optiperf.microservices.core.account.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Objects;

@Entity
@Table(name = "account_balance")
public class BalanceDetails {

    @Id
    @NotNull
    @Column(name = "account_number")
    private Integer accountNumber;

    @NotNull(message = "currentBalance cannot be null.")
    @DecimalMin(value = "0.00", message = "currentBalance must be greater than or equal to 0.00.")
    private BigDecimal currentBalance;

    @NotNull(message = "availableBalance cannot be null.")
    @DecimalMin(value = "0.00", message = "availableBalance must be greater than or equal to 0.00.")
    private BigDecimal availableBalance;

    @NotNull(message = "lastTransactionDate cannot be null.")
    private OffsetDateTime lastTransactionDate;

    // Getters and Setters
    public Integer getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Integer accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }

    public BigDecimal getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(BigDecimal availableBalance) {
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
               "accountNumber=" + accountNumber +
               ", currentBalance=" + currentBalance +
               ", availableBalance=" + availableBalance +
               ", lastTransactionDate=" + lastTransactionDate +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BalanceDetails that = (BalanceDetails) o;
        return Objects.equals(accountNumber, that.accountNumber) &&
               Objects.equals(currentBalance, that.currentBalance) &&
               Objects.equals(availableBalance, that.availableBalance) &&
               Objects.equals(lastTransactionDate, that.lastTransactionDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber, currentBalance, availableBalance, lastTransactionDate);
    }
}