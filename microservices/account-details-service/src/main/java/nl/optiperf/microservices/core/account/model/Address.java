package nl.optiperf.microservices.core.account.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

@Entity
@Table(name = "account_address")
public class Address {

    @Id
    @NotNull
    @Column(name = "account_number")
    private Integer accountNumber;

    @NotNull(message = "Street cannot be null.")
    @NotEmpty(message = "Street cannot be empty.")
    private String street;

    @NotNull(message = "House number cannot be null.")
    @NotEmpty(message = "House number cannot be empty.")
    private String houseNumber;

    @NotNull(message = "City cannot be null.")
    @NotEmpty(message = "City cannot be empty.")
    private String city;

    @NotNull(message = "State cannot be null.")
    @NotEmpty(message = "State cannot be empty.")
    private String state;

    @NotNull(message = "Postal code cannot be null.")
    @NotEmpty(message = "Postal code cannot be empty.")
    private String postalCode;

    @NotNull(message = "Country cannot be null.")
    @NotEmpty(message = "Country cannot be empty.")
    private String country;

    @NotNull(message = "Phone cannot be null.")
    @NotEmpty(message = "Phone cannot be empty.")
    private String phone;

    @NotNull(message = "Email cannot be null.")
    @NotEmpty(message = "Email cannot be empty.")
    private String email;

    // Getters and Setters
    public Integer getAccountNumber() { return accountNumber; }
    public void setAccountNumber(Integer accountNumber) { this.accountNumber = accountNumber; }

    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }

    public String getHouseNumber() { return houseNumber; }
    public void setHouseNumber(String houseNumber) { this.houseNumber = houseNumber; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return "Address{" +
               "accountNumber=" + accountNumber +
               ", street='" + street + '\'' +
               ", houseNumber='" + houseNumber + '\'' +
               ", city='" + city + '\'' +
               ", state='" + state + '\'' +
               ", postalCode='" + postalCode + '\'' +
               ", country='" + country + '\'' +
               ", phone='" + phone + '\'' +
               ", email='" + email + '\'' +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(accountNumber, address.accountNumber) &&
               Objects.equals(street, address.street) &&
               Objects.equals(houseNumber, address.houseNumber) &&
               Objects.equals(city, address.city) &&
               Objects.equals(state, address.state) &&
               Objects.equals(postalCode, address.postalCode) &&
               Objects.equals(country, address.country) &&
               Objects.equals(phone, address.phone) &&
               Objects.equals(email, address.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber, street, houseNumber, city, state, postalCode, country, phone, email);
    }
}