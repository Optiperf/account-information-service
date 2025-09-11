package nl.optiperf.microservices.core.balance.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.Valid;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Embedded;

@Entity
public class AccountBalance {

    @Id
    @NotNull(message = "Account number cannot be null.")
    private Integer accountNumber;

    @Embedded
    @Valid
    @NotNull(message = "Balance details cannot be null.")
    private BalanceDetails balanceDetails;

    // Constructors
    public AccountBalance() {}

    public AccountBalance(Integer accountNumber, BalanceDetails balanceDetails) {
        this.accountNumber = accountNumber;
        this.balanceDetails = balanceDetails;
    }

    // Getters and Setters
    public Integer getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Integer accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BalanceDetails getBalanceDetails() {
        return balanceDetails;
    }

    public void setBalanceDetails(BalanceDetails balanceDetails) {
        this.balanceDetails = balanceDetails;
    }

    @Override
    public String toString() {
        return "AccountBalance{" +
                "accountNumber=" + accountNumber +
                ", balanceDetails=" + balanceDetails +
                '}';
    }
}
