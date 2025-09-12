package nl.optiperf.microservices.core.balance;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
//import org.checkerframework.checker.units.qual.min;
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
import org.springframework.web.server.ResponseStatusException;
import nl.optiperf.microservices.core.balance.GlobalExceptionHandler;

@RestController
@RequestMapping("/account-balance")
public class AccountBalanceServiceController {
    
    private AccountBalanceRepository accountBalanceRepository;
    @Autowired
    public AccountBalanceServiceController(AccountBalanceRepository accountBalanceRepository) {
        this.accountBalanceRepository = accountBalanceRepository;
    }
    @GetMapping
    public ResponseEntity<List<AccountBalance>> getFilteredBalances(
        @RequestParam(required = false) String status,
        @RequestParam(required = false) String accountType,
        @RequestParam(required = false) String currency,
        @RequestParam(required = false) Double minCurrentBalance,
        @RequestParam(required = false) Double maxCurrentBalance,
        @RequestParam(required = false) Double minAvailableBalance,
        @RequestParam(required = false) Double maxAvailableBalance,
        @RequestParam(required = false) OffsetDateTime createdAfter,
        @RequestParam(required = false) OffsetDateTime createdBefore,
        @RequestParam(defaultValue = "0") int offset,
        @RequestParam(defaultValue = "20") int limit
    ) {
        Pageable pageable = PageRequest.of(offset / limit, limit, Sort.by("lastTransactionDate").descending());
        // Provide null/default values for missing parameters to match the method signature
        List<AccountBalance> results = accountBalanceRepository.findFiltered(
            minCurrentBalance,
            maxCurrentBalance,
            minAvailableBalance,
            maxAvailableBalance,
            pageable
        );
        return ResponseEntity.ok(results);
    }
    @GetMapping("/{accountNumber}")
    public ResponseEntity<AccountBalance> getAccountBalance(@PathVariable Integer accountNumber) {
        return accountBalanceRepository.findById(accountNumber)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Balance record not found for account number: " + accountNumber));
    }

    @GetMapping("/{accountNumber}/limited-details")
    public ResponseEntity<?> getLimitedAccountBalanceDetails(@PathVariable Integer accountNumber) {
        return accountBalanceRepository.findById(accountNumber)
                .map(accountBalance -> {
                    BalanceDetails balanceDetails = accountBalance.getBalanceDetails();
                    LimitedBalanceDetailsDTO limitedDetails = new LimitedBalanceDetailsDTO(
                            balanceDetails.getCurrentBalance(),
                            balanceDetails.getAvailableBalance()
                    );
                    return ResponseEntity.ok(limitedDetails);
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping("/{accountNumber}")
    public ResponseEntity<?> createAccountBalance(@PathVariable Integer accountNumber, @RequestBody BalanceDetails balanceDetails) {
        if (accountBalanceRepository.existsById(accountNumber)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Balance creation failed: Account with number " + accountNumber + " already exists. Try updating instead.");
        }

        AccountBalance accountBalance = new AccountBalance();
        accountBalance.setAccountNumber(accountNumber);
        accountBalance.setBalanceDetails(balanceDetails);

        AccountBalance createdAccountBalance = accountBalanceRepository.save(accountBalance);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAccountBalance);
    }

    @PutMapping("/{accountNumber}")
    public ResponseEntity<?> updateAccountBalance(@PathVariable Integer accountNumber, @Valid @RequestBody BalanceDetails updateDetails) {
        AccountBalance existingAccount = accountBalanceRepository.findById(accountNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Balance update failed: Account with number " + accountNumber + " not found."));

        BalanceDetails existingBalanceDetails = existingAccount.getBalanceDetails();
        existingBalanceDetails.setCurrentBalance(updateDetails.getCurrentBalance());
        existingBalanceDetails.setAvailableBalance(updateDetails.getAvailableBalance());
        existingBalanceDetails.setLastTransactionDate(updateDetails.getLastTransactionDate());

        accountBalanceRepository.save(existingAccount);
        return ResponseEntity.ok("Balance updated successfully for account number " + accountNumber);
    }

    @DeleteMapping("/{accountNumber}")
    public ResponseEntity<Void> deleteAccountBalance(@PathVariable Integer accountNumber) {
        if (!accountBalanceRepository.existsById(accountNumber)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Balance deletion failed: Account with number " + accountNumber + " not found.");
        }

        accountBalanceRepository.deleteById(accountNumber);
        return ResponseEntity.noContent().build();
    }
}
