package nl.optiperf.microservices.core.account.model;

import java.time.OffsetDateTime;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import java.util.Objects;

@Entity

public class AccountDetails {
    @Id
    @NotNull(message = "Account number cannot be null.")
    private Integer accountNumber;

    @NotNull(message = "Account name cannot be null.")
    @NotEmpty(message = "Account name cannot be empty.")
    private String accountName;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Account type cannot be null.")
    private AccountType accountType;

    @NotNull(message = "Currency cannot be null.")
    @NotEmpty(message = "Currency cannot be empty.")
    private String currency;

    @NotNull(message = "Status cannot be null.")
    @NotEmpty(message = "Status cannot be empty.")
    private String status;

    @NotNull(message = "Created date cannot be null.")
    private OffsetDateTime createdDate;

    public enum AccountType {
        SAVINGS,             // Standard interest-bearing deposit account
        CHECKING,            // Transactional account for daily use
        LOAN,                // Account tied to borrowed funds (personal, auto, etc.)
        CREDIT,              // Credit card or revolving credit account
        FIXED_DEPOSIT,       // Time-bound deposit with fixed interest
        RECURRING_DEPOSIT,   // Monthly deposit account with fixed tenure
        MORTGAGE,            // Home loan account
        OVERDRAFT,           // Linked to checking, allows negative balance
        INVESTMENT,          // Brokerage or mutual fund account
        RETIREMENT,          // Pension or retirement savings (e.g. IRA, 401k equivalent)
        NRI,                 // Non-resident Indian account (specific to Indian banks)
        ESCROW,              // Third-party holding account for transactions
        JOINT,               // Shared ownership between two or more individuals
        BUSINESS,            // Corporate or SME account
        CURRENT,             // Non-interest-bearing business account (common in EU/Asia)
        SALARY,              // Employer-linked payroll account
        STUDENT,             // Special account for students with limited features
        PREPAID,             // Reloadable card-linked account
        TRUST,               // Managed by trustee for beneficiary
        CHARITY,             // Non-profit or donation-linked account
        FOREX,               // Foreign currency account
        CRYPTO,              // Digital asset wallet or linked account
        DEMAT,               // Securities account for holding shares electronically
        TAX,                 // Account for tax payments or refunds
        SUBSCRIPTION,        // Linked to recurring service payments
        VIRTUAL,             // Non-physical account used for internal routing
        SAFEKEEPING          // Custodial account for valuables or documents
    }
    // Constructors, Getters, Setters, equals, hashCode, toString

        public AccountDetails() {
            }

        public AccountDetails(Integer accountNumber, String accountName, AccountType accountType, String currency, String status, OffsetDateTime createdDate) {
            this.accountNumber = accountNumber;
            this.accountName = accountName;
            this.accountType = accountType;
            this.currency = currency;
            this.status = status;
            this.createdDate = createdDate;
            }

            // Getters and Setters
            public Integer getAccountNumber() { return accountNumber; }
            public void setAccountNumber(Integer accountNumber) { this.accountNumber = accountNumber; }
            public String getAccountName() { return accountName; }
            public void setAccountName(String accountName) { this.accountName = accountName; }
            public AccountType getAccountType() { return accountType; }
            public void setAccountType(AccountType accountType) { this.accountType = accountType; }
            public String getCurrency() { return currency; }
            public void setCurrency(String currency) { this.currency = currency; }
            public String getStatus() { return status; }
            public void setStatus(String status) { this.status = status; }
            public OffsetDateTime getCreatedDate() { return createdDate; }
            public void setCreatedDate(OffsetDateTime createdDate) { this.createdDate = createdDate; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountDetails that = (AccountDetails) o;
        return Objects.equals(accountNumber, that.accountNumber) &&
               Objects.equals(accountName, that.accountName) &&
               accountType == that.accountType &&
               Objects.equals(currency, that.currency) &&
               Objects.equals(status, that.status) &&
               Objects.equals(createdDate, that.createdDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber, accountName, accountType, currency, status, createdDate);
    }

    @Override
    public String toString() {
        return "AccountDetails{" +
               "accountNumber=" + accountNumber +
               ", accountName='" + accountName + '\'' +
               ", accountType=" + accountType +
               ", currency='" + currency + '\'' +
               ", status='" + status + '\'' +
               ", createdDate=" + createdDate +
               '}';
    }
    }
