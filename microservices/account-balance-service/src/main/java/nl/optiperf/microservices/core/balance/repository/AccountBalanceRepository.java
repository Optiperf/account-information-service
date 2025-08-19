package nl.optiperf.microservices.core.balance.repository;
import nl.optiperf.microservices.core.balance.model.AccountBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountBalanceRepository extends JpaRepository<AccountBalance, Integer> {
    // Add other methods as needed, like Page<AccountDetails> findAll(Pageable pageable);
    // Spring Data JPA will automatically implement basic CRUD methods.
    // You can define custom query methods here by following naming conventions or using @Query.
}
