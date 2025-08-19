package nl.optiperf.microservices.core.account.model;
import java.time.OffsetDateTime;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import java.util.Objects;
// import jakarta.persistence.GeneratedValue; // If you want DB to generate ID
// import jakarta.persistence.GenerationType; // If you want DB to generate ID

@Entity
public class AccountDetails {
    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY) // Uncomment if you want the DB to generate the ID
    @NotNull(message = "Account number cannot be null.")
    private Integer accountNumber; // Assuming accountNumber is the ID
    @NotNull(message = "Account name cannot be null.")
    @NotEmpty(message = "Account name cannot be empty.")
    private String accountName;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Account type cannot be null.")
    private AccountType accountType; // Assuming AccountType is an enum

    @NotNull(message = "Currency cannot be null.")
    @NotEmpty(message = "Currency cannot be empty.")
    private String currency;
    @NotNull(message = "Status cannot be null.")
    @NotEmpty(message = "Status cannot be empty.")
    private String status;
    
    @NotNull(message = "Created date cannot be null.")
    private OffsetDateTime createdDate;

    public enum AccountType {
    SAVINGS,
    CHECKING,
    CREDIT,
    LOAN;
    // You can add additional methods or properties if needed
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
