package nl.optiperf.microservices.core.address.model;

import jakarta.validation.constraints.NotNull;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Embedded;
import jakarta.validation.Valid;
import java.util.Objects;
// import jakarta.persistence.GeneratedValue; // If you want DB to generate ID
// import jakarta.persistence.GenerationType; // If you want DB to generate ID

@Entity
public class AccountAddress {
    @Id
    private Integer accountNumber;
    
    @Embedded
    @Valid // Enable validation for fields within AddressDetails
    @NotNull(message = "Address details cannot be null.")
    private AddressDetails address;

    @Embedded
    @Valid // Enable validation for fields within ContactDetails
    @NotNull(message = "Contact details cannot be null.")
    private ContactDetails contactDetails;
    // Constructors, Getters, Setters, equals, hashCode, toString

    public AccountAddress() {
    }

    public AccountAddress(Integer accountNumber, AddressDetails address, ContactDetails contactDetails) {
        this.accountNumber = accountNumber;
        this.address = address;
        this.contactDetails = contactDetails;
    }

    // Getters and Setters
    public Integer getAccountNumber() { return accountNumber; }
    public void setAccountNumber(Integer accountNumber) { this.accountNumber = accountNumber; }
    public AddressDetails getAddress() { return address; }
    public void setAddress(AddressDetails address) { this.address = address; }

    public ContactDetails getContactDetails() { return contactDetails; }
    public void setContactDetails(ContactDetails contactDetails) { this.contactDetails = contactDetails; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountAddress that = (AccountAddress) o;
        return Objects.equals(accountNumber, that.accountNumber) &&
               Objects.equals(address, that.address) &&
               Objects.equals(contactDetails, that.contactDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber, address, contactDetails);
    }

    @Override
    public String toString() {
        return "AccountAddress{" +
               "accountNumber=" + accountNumber +
               ", address=" + address +
               ", contactDetails=" + contactDetails +
               '}';
    }
 }
