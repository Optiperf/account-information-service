package nl.optiperf.microservices.core.account.model;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class ContactDetails {

    private String phone;
    private String email;

    // Getters and Setters
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return "ContactDetails{" +
               "phone='" + phone + '\'' +
               ", email='" + email + '\'' +
               '}';
    }

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
}