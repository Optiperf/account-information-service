package nl.optiperf.microservices.core.address.model;

import java.util.Objects;

// DTO for GET /account-address/{accountNumber}/contact-details response
public class ContactDetailsDTO {
    private String phone;
    private String email;

    public ContactDetailsDTO() {
    }

    public ContactDetailsDTO(String phone, String email) {
        this.phone = phone;
        this.email = email;
    }

    // Getters
    public String getPhone() { return phone; }
    public String getEmail() { return email; }

    // Setters (optional, but useful for some frameworks/deserialization)
    public void setPhone(String phone) { this.phone = phone; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactDetailsDTO that = (ContactDetailsDTO) o;
        return Objects.equals(phone, that.phone) &&
               Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phone, email);
    }

    @Override
    public String toString() {
        return "ContactDetailsDTO{" +
               "phone='" + phone + '\'' +
               ", email='" + email + '\'' +
               '}';
    }
}