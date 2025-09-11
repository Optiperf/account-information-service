package nl.optiperf.microservices.core.balance.repository;

import nl.optiperf.microservices.core.balance.model.AccountBalance;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AccountBalanceCustomRepository {

    List<AccountBalance> findFiltered(Double minCurrentBalance, Double maxCurrentBalance,
                                      Double minAvailableBalance, Double maxAvailableBalance,
                                      Pageable pageable);
}