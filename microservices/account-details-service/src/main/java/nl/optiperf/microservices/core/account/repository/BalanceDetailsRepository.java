package nl.optiperf.microservices.core.account.repository;

import nl.optiperf.microservices.core.account.model.BalanceDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BalanceDetailsRepository extends JpaRepository<BalanceDetails, Long> {
    void deleteByAccountNumber(Integer accountNumber);
}