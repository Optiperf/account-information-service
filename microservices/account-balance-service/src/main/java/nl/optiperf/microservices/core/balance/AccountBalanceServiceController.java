package nl.optiperf.microservices.core.balance;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import nl.optiperf.microservices.core.balance.model.AccountBalance;
import nl.optiperf.microservices.core.balance.model.BalanceDetails;
import nl.optiperf.microservices.core.balance.model.LimitedBalanceDetailsDTO;
import nl.optiperf.microservices.core.balance.model.UpdateBalanceRequestDTO;
import nl.optiperf.microservices.core.balance.repository.AccountBalanceRepository;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/account-balance")
public class AccountBalanceServiceController {
    
    private AccountBalanceRepository accountBalanceRepository;
    @Autowired
    public AccountBalanceServiceController(AccountBalanceRepository accountBalanceRepository) {
        this.accountBalanceRepository = accountBalanceRepository;
    }
    @GetMapping("/{accountNumber}")
    public ResponseEntity<AccountBalance> getAccountBalance(@PathVariable Integer accountNumber) {
        return accountBalanceRepository.findById(accountNumber)
                .map(accountBalance -> ResponseEntity.ok(accountBalance))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/{accountNumber}/limited-details")
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

    @PostMapping("/{accountNumber}")
    public ResponseEntity<?> createAccountBalance(@PathVariable Integer accountNumber, @RequestBody AccountBalance accountBalance) {
        if (accountBalanceRepository.existsById(accountNumber)) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "Balance creation failed");
            errorResponse.put("detail", "Account with number " + accountNumber + " already exists. Try updating instead.");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        }
        if (!accountNumber.equals(accountBalance.getAccountNumber())) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "Balance creation failed");
            errorResponse.put("detail", "Account number in URL (" + accountNumber + ") does not match account number in request body (" + accountBalance.getAccountNumber() + ").");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
        
        accountBalance.setAccountNumber(accountNumber);
        AccountBalance createdAccountBalance = accountBalanceRepository.save(accountBalance);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAccountBalance);
    }

    @PutMapping("/{accountNumber}")
    public ResponseEntity<?> updateAccountBalance(@PathVariable Integer accountNumber, @Valid @RequestBody UpdateBalanceRequestDTO updateDetails) {
        // Fetch the existing account balance entity
        Optional<AccountBalance> existingAccountOptional = accountBalanceRepository.findById(accountNumber);

        if (!existingAccountOptional.isPresent()) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "Balance update failed");
            errorResponse.put("detail", "Account with number " + accountNumber + " not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
        AccountBalance existingAccount = existingAccountOptional.get();
        BalanceDetails existingBalanceDetails = existingAccount.getBalance();

        // Check if the currency in the request matches the existing currency
        if (!existingBalanceDetails.getCurrency().equals(updateDetails.getCurrency())) {
             Map<String, Object> errorResponse = new HashMap<>();
             errorResponse.put("message", "Balance update failed");
             errorResponse.put("detail", "Currency mismatch. Cannot change currency from '" + existingBalanceDetails.getCurrency() + "' to '" + updateDetails.getCurrency() + "'.");
             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        // Update only the current and available balance fields
        existingBalanceDetails.setCurrentBalance(updateDetails.getCurrentBalance());
        existingBalanceDetails.setAvailableBalance(updateDetails.getAvailableBalance());

        // Save the updated entity. Other fields (name, type, status, lastTransactionDate) remain unchanged.
        accountBalanceRepository.save(existingAccount);
        Map<String, String> successResponse = new HashMap<>();
        successResponse.put("message", "Balance updated successfully for account number " + accountNumber);
        return ResponseEntity.ok(successResponse);
    }
}
