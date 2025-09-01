package nl.optiperf.microservices.core.address.repository;

import nl.optiperf.microservices.core.address.model.AccountAddress;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface AccountAddressCustomRepository {
    List<AccountAddress> findFiltered(String city, String country,
                                      OffsetDateTime createdAfter, OffsetDateTime createdBefore, Pageable pageable);
}