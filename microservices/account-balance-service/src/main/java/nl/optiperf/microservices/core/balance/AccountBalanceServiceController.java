package nl.optiperf.microservices.core.balance;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import nl.optiperf.microservices.core.balance.model.AccountBalance;
import nl.optiperf.microservices.core.balance.model.BalanceDetails;
import nl.optiperf.microservices.core.balance.model.LimitedBalanceDetailsDTO;
import nl.optiperf.microservices.core.balance.model.UpdateBalanceRequestDTO;
import nl.optiperf.microservices.core.balance.repository.AccountBalanceRepository;
import java.util.Optional;
import java.util.Map;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@RestController
@RequestMapping("/account-balance")
public class AccountBalanceServiceController {
    
    private AccountBalanceRepository accountBalanceRepository;
    private final String ACCOUNT_DETAILS_SERVICE_URL = "http://kong:8000/account-details/exists";
    private RestTemplate restTemplate;

    @Autowired
    public AccountBalanceServiceController(AccountBalanceRepository accountBalanceRepository, RestTemplate restTemplate) {
        this.accountBalanceRepository = accountBalanceRepository;
        this.restTemplate = restTemplate;
    }
    @GetMapping
    public ResponseEntity<List<AccountBalance>> getFilteredBalances(
        @RequestParam(required = false) Double minCurrentBalance,
        @RequestParam(required = false) Double maxCurrentBalance,
        @RequestParam(required = false) Double minAvailableBalance,
        @RequestParam(required = false) Double maxAvailableBalance,
        @RequestParam(required = false) OffsetDateTime createdAfter,
        @RequestParam(required = false) OffsetDateTime createdBefore,
        @RequestParam(defaultValue = "0") int offset,
        @RequestParam(defaultValue = "20") int limit
    ) {
        Pageable pageable = PageRequest.of(offset / limit, limit, Sort.by("balanceDetails.lastTransactionDate").descending());

        // Adjusted to use only the available fields
        List<AccountBalance> results = accountBalanceRepository.findFiltered(
            minCurrentBalance,
            maxCurrentBalance,
            minAvailableBalance,
            maxAvailableBalance,
            createdAfter,
            createdBefore,
            pageable
        );
        return ResponseEntity.ok(results);
    }
    @GetMapping("/{accountNumber}")
    public ResponseEntity<AccountBalance> getAccountBalance(@PathVariable Integer accountNumber) {
        return accountBalanceRepository.findById(accountNumber)
                .map(accountBalance -> ResponseEntity.ok(accountBalance))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

  /*  @GetMapping("/{accountNumber}/limited-details")
    public ResponseEntity<?> getLimitedAccountBalanceDetails(@PathVariable Integer accountNumber) {
        return accountBalanceRepository.findById(accountNumber)
                .map(accountBalance -> {
                    // The AccountBalance entity has a @NotNull constraint on its 'balance' field.
                    // Therefore, accountBalance.getBalance() should not be null if the entity is valid.
                    BalanceDetails balanceDetails = accountBalance.getBalance();
                    LimitedBalanceDetailsDTO limitedDetails = new LimitedBalanceDetailsDTO(
                            balanceDetails.getCurrency(),
                            balanceDetails.getCurrentBalance(),
                            balanceDetails.getAvailableBalance()
                    );
                    return ResponseEntity.ok(limitedDetails);
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
*/
    @PostMapping("/{accountNumber}")
    public ResponseEntity<?> createAccountBalance(
            @PathVariable Integer accountNumber,
            @RequestBody BalanceDetails balanceDetails) {

        // Validate and process the request
        AccountBalance accountBalance = new AccountBalance();
        accountBalance.setAccountNumber(accountNumber);
        accountBalance.setBalanceDetails(balanceDetails);

        // Save the balance record
        AccountBalance createdAccountBalance = accountBalanceRepository.save(accountBalance);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAccountBalance);
    }

    @PutMapping("/{accountNumber}")
    public ResponseEntity<?> updateAccountBalance(@PathVariable Integer accountNumber, @Valid @RequestBody BalanceDetails updateDetails) {
        // Fetch the existing account balance entity
        Optional<AccountBalance> existingAccountOptional = accountBalanceRepository.findById(accountNumber);

        if (!existingAccountOptional.isPresent()) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "Balance update failed");
            errorResponse.put("detail", "Account with number " + accountNumber + " not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

        AccountBalance existingAccount = existingAccountOptional.get();
        existingAccount.setBalanceDetails(updateDetails);

        // Save the updated entity
        accountBalanceRepository.save(existingAccount);
        Map<String, String> successResponse = new HashMap<>();
        successResponse.put("message", "Balance updated successfully for account number " + accountNumber);
        return ResponseEntity.ok(successResponse);
    }
}

