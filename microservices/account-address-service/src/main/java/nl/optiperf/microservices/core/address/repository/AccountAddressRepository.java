package nl.optiperf.microservices.core.address.repository;
import nl.optiperf.microservices.core.address.model.AccountAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountAddressRepository extends JpaRepository<AccountAddress, Integer> {
    // Add other methods as needed, like Page<AccountDetails> findAll(Pageable pageable);
    // Spring Data JPA will automatically implement basic CRUD methods.
    // You can define custom query methods here by following naming conventions or using @Query.
}
