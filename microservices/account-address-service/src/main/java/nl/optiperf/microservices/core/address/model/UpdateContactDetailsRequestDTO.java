package nl.optiperf.microservices.core.address.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Objects;

// DTO for PUT /account-address/{accountNumber}/contact-details request body
public class UpdateContactDetailsRequestDTO {

    @NotNull(message = "Phone number cannot be null.")
    @NotEmpty(message = "Phone number cannot be empty.")
    private String phone;

    @NotNull(message = "Email address cannot be null.")
    @NotEmpty(message = "Email address cannot be empty.")
    // Consider adding a @Email annotation for format validation if needed
    private String email;

    public UpdateContactDetailsRequestDTO() {
    }

    public UpdateContactDetailsRequestDTO(String phone, String email) {
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
        UpdateContactDetailsRequestDTO that = (UpdateContactDetailsRequestDTO) o;
        return Objects.equals(phone, that.phone) &&
               Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phone, email);
    }

    // toString omitted for brevity
}