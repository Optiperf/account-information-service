package nl.optiperf.microservices.core.account;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import nl.optiperf.microservices.core.account.model.AccountDetails;
import nl.optiperf.microservices.core.account.repository.AccountDetailsRepository;
import nl.optiperf.microservices.core.account.model.BalanceDetails;
import nl.optiperf.microservices.core.account.model.Address;
import nl.optiperf.microservices.core.account.repository.BalanceDetailsRepository;
import nl.optiperf.microservices.core.account.repository.AddressRepository;
import java.util.Map;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import java.math.BigDecimal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/account-details")
public class AccountDetailsServiceController {

    private final AccountDetailsRepository accountDetailsRepository;
    private final BalanceDetailsRepository balanceDetailsRepository;
    private final AddressRepository addressRepository;

    @Autowired
    public AccountDetailsServiceController(AccountDetailsRepository accountDetailsRepository,
                                           BalanceDetailsRepository balanceDetailsRepository,
                                           AddressRepository addressRepository) {
        this.accountDetailsRepository = accountDetailsRepository;
        this.balanceDetailsRepository = balanceDetailsRepository;
        this.addressRepository = addressRepository;
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
    public ResponseEntity<?> getAccountDetails(@PathVariable Integer accountNumber) {
        return accountDetailsRepository.findById(accountNumber)
                .map(accountDetails -> ResponseEntity.ok(accountDetails))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Account record not found for account number: " + accountNumber));
    }
    @PostMapping("/{accountNumber}")
    @Transactional
    public ResponseEntity<?> createAccountDetails(@PathVariable Integer accountNumber, @RequestBody Map<String, Object> payload) {
        if (accountDetailsRepository.existsById(accountNumber)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Account creation failed: Account with number " + accountNumber + " already exists.");
        }

        // Extract and save account details
        AccountDetails accountDetails = new AccountDetails();
        accountDetails.setAccountNumber(accountNumber);
        accountDetails.setAccountName((String) payload.get("accountName"));
        accountDetails.setAccountType(AccountDetails.AccountType.valueOf((String) payload.get("accountType")));
        accountDetails.setCurrency((String) payload.get("currency"));
        accountDetails.setStatus((String) payload.get("status"));
        accountDetails.setCreatedDate(OffsetDateTime.parse((String) payload.get("createdDate")));
        accountDetailsRepository.save(accountDetails);

        // Extract and save balance details
        Map<String, Object> balanceDetailsMap = (Map<String, Object>) payload.get("balanceDetails");
        if (balanceDetailsMap != null) {
            BalanceDetails balanceDetails = new BalanceDetails();
            balanceDetails.setAccountNumber(accountNumber);
            balanceDetails.setCurrentBalance(new BigDecimal(balanceDetailsMap.get("currentBalance").toString()));
            balanceDetails.setAvailableBalance(new BigDecimal(balanceDetailsMap.get("availableBalance").toString()));
            balanceDetails.setLastTransactionDate(OffsetDateTime.parse((String) balanceDetailsMap.get("lastTransactionDate")));
            balanceDetailsRepository.save(balanceDetails);
        }

        // Extract and save address details
        Map<String, Object> addressMap = (Map<String, Object>) payload.get("address");
        Map<String, Object> contactDetailsMap = (Map<String, Object>) payload.get("contactDetails");
        if (addressMap != null) {
            Address address = new Address();
            address.setAccountNumber(accountNumber);
            address.setStreet((String) addressMap.get("street"));
            address.setHouseNumber((String) addressMap.get("houseNumber"));
            address.setCity((String) addressMap.get("city"));
            address.setState((String) addressMap.get("state"));
            address.setPostalCode((String) addressMap.get("postalCode"));
            address.setCountry((String) addressMap.get("country"));
            if (contactDetailsMap != null) {
                address.setPhone((String) contactDetailsMap.get("phone"));
                address.setEmail((String) contactDetailsMap.get("email"));
            }
            addressRepository.save(address);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("Account, Balance, and Address created successfully.");
    }

    @PutMapping("/{accountNumber}")
    public ResponseEntity<?> updateAccountDetails(@PathVariable Integer accountNumber, @Valid @RequestBody AccountDetails accountDetails) {
        if (!accountDetailsRepository.existsById(accountNumber)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Account update failed: Account with number " + accountNumber + " not found.");
        }

        // Update account details
        accountDetails.setAccountNumber(accountNumber);
        accountDetailsRepository.save(accountDetails);

        return ResponseEntity.ok("Account updated successfully.");
    }

    @Transactional
    @DeleteMapping("/{accountNumber}")
    public ResponseEntity<?> deleteAccountDetails(@PathVariable Integer accountNumber) {
        if (!accountDetailsRepository.existsById(accountNumber)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Account deletion failed: Account with number " + accountNumber + " not found.");
        }

        // Delete account details
        accountDetailsRepository.deleteById(accountNumber);

        // Delete balance details
        balanceDetailsRepository.deleteByAccountNumber(accountNumber);

        // Delete address details
        addressRepository.deleteByAccountNumber(accountNumber);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}