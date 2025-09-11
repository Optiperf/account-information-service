package nl.optiperf.microservices.core.account;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import nl.optiperf.microservices.core.account.model.AccountDetails;
import nl.optiperf.microservices.core.account.repository.AccountDetailsRepository;
import java.util.Map;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@RestController
@RequestMapping("/account-details")
public class AccountDetailsServiceController {

    private AccountDetailsRepository accountDetailsRepository;
    @Autowired
    public AccountDetailsServiceController(AccountDetailsRepository accountDetailsRepository) {
        this.accountDetailsRepository = accountDetailsRepository;
    }

    // 🔍 New filtering + pagination endpoint
    @GetMapping
    public ResponseEntity<List<AccountDetails>> getFilteredAccounts(
        @RequestParam(required = false) String status,
        @RequestParam(required = false) AccountDetails.AccountType accountType,
        @RequestParam(required = false) String currency,
        @RequestParam(required = false) OffsetDateTime createdAfter,
        @RequestParam(required = false) OffsetDateTime createdBefore,
        @RequestParam(defaultValue = "0") int offset,
        @RequestParam(defaultValue = "20") int limit
    ) {
        Pageable pageable = PageRequest.of(offset / limit, limit, Sort.by("createdDate").descending());
        List<AccountDetails> results = accountDetailsRepository.findFiltered(status, accountType, currency, createdAfter, createdBefore, pageable);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<AccountDetails> getAccountDetails(@PathVariable Integer accountNumber) {
        return accountDetailsRepository.findById(accountNumber)
                .map(accountDetails -> ResponseEntity.ok(accountDetails))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    @PostMapping("/{accountNumber}")
    public ResponseEntity<?> createAccountDetails(@PathVariable Integer accountNumber, @Valid @RequestBody AccountDetails accountDetails) {
        if (accountDetailsRepository.existsById(accountNumber)) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "Account creation failed");
            errorResponse.put("detail", "Account with number " + accountNumber + " already exists.");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        }

        // Check 1: Account number in body must match account number in URL
        if (accountDetails.getAccountNumber() == null) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "Account creation failed");
            errorResponse.put("detail", "Account number must be provided in the request body.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
        if (!accountNumber.equals(accountDetails.getAccountNumber())) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "Account creation failed");
            errorResponse.put("detail", "Account number in URL (" + accountNumber + ") does not match account number in request body (" + accountDetails.getAccountNumber() + ").");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
        
        // Save the account details
        accountDetails.setAccountNumber(accountNumber);
        AccountDetails savedAccount = accountDetailsRepository.save(accountDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAccount);
    }
    @PutMapping("/{accountNumber}")
    public ResponseEntity<?> updateAccountDetails(@PathVariable Integer accountNumber, @Valid @RequestBody AccountDetails accountDetails) {
        // Check 1: Account number in body must match account number in URL
        if (accountDetails.getAccountNumber() == null) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "Account update failed");
            errorResponse.put("detail", "Account number must be provided in the request body.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
        if (!accountNumber.equals(accountDetails.getAccountNumber())) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "Account update failed");
            errorResponse.put("detail", "Account number in URL (" + accountNumber + ") does not match account number in request body (" + accountDetails.getAccountNumber() + ").");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        // Check 2: Account must exist to be updated
        if (!accountDetailsRepository.existsById(accountNumber)) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "Account update failed");
            errorResponse.put("detail", "Account with number " + accountNumber + " not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
        accountDetails.setAccountNumber(accountNumber);
        AccountDetails updatedAccount = accountDetailsRepository.save(accountDetails);
        return ResponseEntity.ok(updatedAccount);
    }
    @DeleteMapping("/{accountNumber}")
    public ResponseEntity<Void> deleteAccountDetails(@PathVariable Integer accountNumber) {
        if (!accountDetailsRepository.findById(accountNumber).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        accountDetailsRepository.deleteById(accountNumber);
        return ResponseEntity.noContent().build();
    }

  
}