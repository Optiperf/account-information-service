package nl.optiperf.microservices.core.balance.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.Embedded;
import jakarta.validation.Valid;
// import jakarta.persistence.GeneratedValue; // If you want DB to generate ID
// import jakarta.persistence.GenerationType; // If you want DB to generate ID
/*
    
        "accountNumber": 125,
        "accountName": "John Doe",
        "balance": {
            "currency": "EUR",
            "currentBalance": 2500.75,
            "availableBalance": 2400.00,
            "lastTransactionDate": "2025-05-30T14:22:00Z"
        },
        "accountStatus": "Active",
        "accountType": "CHECKING"
    }
 */


@Entity
public class AccountBalance {
    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY) // Uncomment if you want the DB to generate the ID
    @NotNull(message = "Account number cannot be null.")
    private Integer accountNumber; // Assuming accountNumber is the ID
    @NotNull(message = "Account name cannot be null.")
    @NotEmpty(message = "Account name cannot be empty.")
    private String accountName;

    @Embedded
    @Valid // Add this annotation to enable validation of BalanceDetails fields
    @NotNull(message = "Balance details cannot be null.")
    private BalanceDetails balance;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Account type cannot be null.")
    private AccountType accountType; // Assuming AccountType is an enum

    @NotNull(message = "Status cannot be null.")
    @NotEmpty(message = "Status cannot be empty.")
    private String status;

    public enum AccountType {
    SAVINGS,
    CHECKING,
    CREDIT,
    LOAN;
    // You can add additional methods or properties if needed
}
    // Constructors, Getters, Setters, equals, hashCode, toString

        public AccountBalance() {
            }

        public AccountBalance(Integer accountNumber, String accountName, BalanceDetails balance, AccountType accountType, String status) {
            this.accountNumber = accountNumber;
            this.accountName = accountName;
            this.balance = balance;
            this.accountType = accountType;
            this.status = status;
            }

            // Getters and Setters
            public Integer getAccountNumber() { return accountNumber; }
            public void setAccountNumber(Integer accountNumber) { this.accountNumber = accountNumber; }
            public String getAccountName() { return accountName; }
            public void setAccountName(String accountName) { this.accountName = accountName; }
            public AccountType getAccountType() { return accountType; }
            public void setAccountType(AccountType accountType) { this.accountType = accountType; }
            public String getStatus() { return status; }
            public void setStatus(String status) { this.status = status; }
            public BalanceDetails getBalance() { return balance; }
            public void setBalance(BalanceDetails balance) { this.balance = balance; }
    }
