package nl.optiperf.microservices.core.account.repository;
import nl.optiperf.microservices.core.account.model.AccountDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountDetailsRepository extends JpaRepository<AccountDetails, Integer> {
    // Add other methods as needed, like Page<AccountDetails> findAll(Pageable pageable);
    // Spring Data JPA will automatically implement basic CRUD methods.
    // You can define custom query methods here by following naming conventions or using @Query.
}