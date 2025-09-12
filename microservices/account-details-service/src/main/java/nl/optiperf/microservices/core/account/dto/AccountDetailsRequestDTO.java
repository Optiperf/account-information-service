package nl.optiperf.microservices.core.account.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import nl.optiperf.microservices.core.account.model.BalanceDetails;
import nl.optiperf.microservices.core.account.model.Address;
import nl.optiperf.microservices.core.account.model.AccountDetails.AccountType;

import java.time.OffsetDateTime;

public class AccountDetailsRequestDTO {

    @NotNull(message = "Account number cannot be null.")
    private Integer accountNumber;

    @NotNull(message = "Account name cannot be null.")
    private String accountName;

    @NotNull(message = "Account type cannot be null.")
    private AccountType accountType;

    @NotNull(message = "Currency cannot be null.")
    private String currency;

    @NotNull(message = "Status cannot be null.")
    private String status;

    @NotNull(message = "Created date cannot be null.")
    private OffsetDateTime createdDate;

    @Valid
    private BalanceDetails balanceDetails;

    @Valid
    private Address address;

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

    public BalanceDetails getBalanceDetails() { return balanceDetails; }
    public void setBalanceDetails(BalanceDetails balanceDetails) { this.balanceDetails = balanceDetails; }

    public Address getAddress() { return address; }
    public void setAddress(Address address) { this.address = address; }
}
