package nl.optiperf.microservices.core.address;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import jakarta.validation.Valid;
import nl.optiperf.microservices.core.address.model.ContactDetails;
import nl.optiperf.microservices.core.address.model.ContactDetailsDTO;
import nl.optiperf.microservices.core.address.model.AccountAddress;
import nl.optiperf.microservices.core.address.model.UpdateContactDetailsRequestDTO;
import nl.optiperf.microservices.core.address.repository.AccountAddressRepository;
import java.util.Map;
import java.util.Optional;
import java.util.HashMap;

@RestController
@RequestMapping("/account-address")
public class AccountAddressServiceController {

    private final AccountAddressRepository accountAddressRepository;
    @Autowired
    public AccountAddressServiceController(AccountAddressRepository accountAddressRepository) {
        this.accountAddressRepository = accountAddressRepository;
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<?> getAccountAddress(@PathVariable Integer accountNumber) {
        return accountAddressRepository.findById(accountNumber)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Address not found for account number: " + accountNumber));
    }

    @PostMapping("/{accountNumber}")
    public ResponseEntity<?> createAccountAddress(@PathVariable Integer accountNumber, @Valid @RequestBody AccountAddress addressDetails) {
        if (accountAddressRepository.existsById(accountNumber)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Address creation failed: Address for account number " + accountNumber + " already exists. Use PUT to update.");
            // Using ResponseStatusException allows the GlobalExceptionHandler to format the response
            // return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse); // Old way
        }

        // Set the account number from the path variable.
        // The AccountAddress object from the request body will only contain address fields.
        addressDetails.setAccountNumber(accountNumber);
        AccountAddress savedAddress = accountAddressRepository.save(addressDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAddress);
    }

    @PutMapping("/{accountNumber}")
    public ResponseEntity<?> updateAccountAddress(@PathVariable Integer accountNumber, @Valid @RequestBody AccountAddress updatedAccountAddress) {
        // Fetch the existing address entity
        Optional<AccountAddress> existingAddressOptional = accountAddressRepository.findById(accountNumber);

        if (!existingAddressOptional.isPresent()) {
             throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Address update failed: Address with account number " + accountNumber + " not found.");
            // return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse); // Old way
        }

        AccountAddress existingAddress = existingAddressOptional.get();

        // Update the embedded AddressDetails and ContactDetails objects
        // Note: This assumes the request body contains the full AddressDetails and ContactDetails objects.
        // If you only want to allow partial updates (e.g., only update address or only contact details),
        // you would need separate DTOs or endpoints, or use PATCH.
        existingAddress.setAddress(updatedAccountAddress.getAddress());
        existingAddress.setContactDetails(updatedAccountAddress.getContactDetails());

        // The accountNumber from the path variable is implicitly used by JPA save on the existing entity.
        // We don't need to set it from the request body DTO anymore.

        // Save the updated entity
        AccountAddress updatedAddress = accountAddressRepository.save(existingAddress);
        return ResponseEntity.ok(updatedAddress);
    }

    @GetMapping("/{accountNumber}/contact-details")
    public ResponseEntity<?> getAccountContactDetails(@PathVariable Integer accountNumber) {
        return accountAddressRepository.findById(accountNumber)
                .map(accountAddress -> {
                    // Map the embedded ContactDetails entity to the ContactDetailsDTO
                    ContactDetails contactDetails = accountAddress.getContactDetails();
                    ContactDetailsDTO contactDetailsDTO = new ContactDetailsDTO(
                            contactDetails.getPhone(),
                            contactDetails.getEmail()
                    );
                    return ResponseEntity.ok(contactDetailsDTO);
                })
                .orElseGet(() -> {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Contact details not found for account number: " + accountNumber);
                });
    }

    @PutMapping("/{accountNumber}/contact-details")
    public ResponseEntity<?> updateAccountContactDetails(@PathVariable Integer accountNumber, @Valid @RequestBody UpdateContactDetailsRequestDTO updateDetails) {
        AccountAddress existingAddress = accountAddressRepository.findById(accountNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Contact details update failed: Address with account number " + accountNumber + " not found."));

        // Update only the contact details fields
        existingAddress.getContactDetails().setPhone(updateDetails.getPhone());
        existingAddress.getContactDetails().setEmail(updateDetails.getEmail());

        accountAddressRepository.save(existingAddress);
        Map<String, String> successResponse = new HashMap<>();
        successResponse.put("message", "Contact details updated successfully for account number " + accountNumber);
        return ResponseEntity.ok(successResponse);
    }

    @DeleteMapping("/{accountNumber}")
    public ResponseEntity<Void> deleteAccountAddress(@PathVariable Integer accountNumber) {
        if (!accountAddressRepository.existsById(accountNumber)) {
            // Optionally, you could return a more detailed error response if preferred
            // Map<String, Object> errorResponse = new HashMap<>();
            // errorResponse.put("message", "Address deletion failed");
            // errorResponse.put("detail", "Address with account number " + accountNumber + " not found.");
             throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Address deletion failed: Address with account number " + accountNumber + " not found.");
        }
        accountAddressRepository.deleteById(accountNumber);
        return ResponseEntity.noContent().build();
    }
}
