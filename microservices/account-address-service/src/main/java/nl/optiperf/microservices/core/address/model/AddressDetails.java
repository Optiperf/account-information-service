package nl.optiperf.microservices.core.address.model;
import java.util.Objects;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Embeddable
public class AddressDetails {

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

    public AddressDetails() {
    }

    public AddressDetails(String street, String houseNumber, String city, String state, String postalCode, String country) {
        this.street = street;
        this.houseNumber = houseNumber;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.country = country;
    }

    // Getters and Setters
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressDetails that = (AddressDetails) o;
        return Objects.equals(street, that.street) &&
               Objects.equals(houseNumber, that.houseNumber) &&
               Objects.equals(city, that.city) &&
               Objects.equals(state, that.state) &&
               Objects.equals(postalCode, that.postalCode) &&
               Objects.equals(country, that.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, houseNumber, city, state, postalCode, country);
    }
}