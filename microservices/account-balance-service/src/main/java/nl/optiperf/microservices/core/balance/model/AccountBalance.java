package nl.optiperf.microservices.core.balance.model;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class AccountBalance {

    @Id
    private Integer accountNumber;

    @Embedded
    private BalanceDetails balanceDetails;

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
}
