package nl.optiperf.microservices.core.balance.repository;

import nl.optiperf.microservices.core.balance.model.AccountBalance;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface AccountBalanceCustomRepository {
    List<AccountBalance> findFiltered(
        String status,
        String accountType,
        String currency,
        Double minCurrentBalance,
        Double maxCurrentBalance,
        Double minAvailableBalance,
        Double maxAvailableBalance,
        OffsetDateTime lastTransactionAfter,
        OffsetDateTime lastTransactionBefore,
        Pageable pageable
    );
}