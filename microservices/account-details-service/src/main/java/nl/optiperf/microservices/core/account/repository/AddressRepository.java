package nl.optiperf.microservices.core.account.repository;

import nl.optiperf.microservices.core.account.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
    void deleteByAccountNumber(Integer accountNumber);
}