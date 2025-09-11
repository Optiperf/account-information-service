package nl.optiperf.microservices.core.balance.repository;

import nl.optiperf.microservices.core.balance.model.AccountBalance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountBalanceRepository extends JpaRepository<AccountBalance, Integer>, AccountBalanceCustomRepository {
}
