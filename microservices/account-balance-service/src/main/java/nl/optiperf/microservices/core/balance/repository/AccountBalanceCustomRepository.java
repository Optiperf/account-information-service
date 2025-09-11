package nl.optiperf.microservices.core.balance.repository;

import nl.optiperf.microservices.core.balance.model.AccountBalance;
import org.springframework.data.domain.Pageable;

import java.time.OffsetDateTime;
import java.util.List;

public interface AccountBalanceCustomRepository {

    List<AccountBalance> findFiltered(Double minCurrentBalance, Double maxCurrentBalance,
                                      Double minAvailableBalance, Double maxAvailableBalance,
                                      OffsetDateTime createdAfter, OffsetDateTime createdBefore,
                                      Pageable pageable);
}