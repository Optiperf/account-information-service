package nl.optiperf.microservices.core.address.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Objects;

@Embeddable
public class ContactDetails {

    @NotNull(message = "Phone number cannot be null.")
    @NotEmpty(message = "Phone number cannot be empty.")
    private String phone;

    @NotNull(message = "Email address cannot be null.")
    @NotEmpty(message = "Email address cannot be empty.")
    // Consider adding a @Email annotation for format validation if needed
    private String email;

    public ContactDetails() {
    }

    public ContactDetails(String phone, String email) {
        this.phone = phone;
        this.email = email;
    }

    // Getters and Setters
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactDetails that = (ContactDetails) o;
        return Objects.equals(phone, that.phone) &&
               Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phone, email);
    }

    @Override
    public String toString() {
        return "ContactDetails{" + "phone='" + phone + '\'' + ", email='" + email + '\'' + '}';
    }
}