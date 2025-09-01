package nl.optiperf.microservices.core.account.repository;
import nl.optiperf.microservices.core.account.model.AccountDetails;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface AccountDetailsCustomRepository {
    List<AccountDetails> findFiltered(String status, AccountDetails.AccountType accountType, String currency,
                                      OffsetDateTime createdAfter, OffsetDateTime createdBefore, Pageable pageable);
}
