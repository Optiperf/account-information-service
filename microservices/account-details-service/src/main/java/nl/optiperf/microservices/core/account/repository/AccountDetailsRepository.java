package nl.optiperf.microservices.core.account.repository;

import nl.optiperf.microservices.core.account.model.AccountDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Extend with custom filtering interface
@Repository
public interface AccountDetailsRepository extends JpaRepository<AccountDetails, Integer>, AccountDetailsCustomRepository {
    // You can still use all default CRUD methods from JpaRepository
}