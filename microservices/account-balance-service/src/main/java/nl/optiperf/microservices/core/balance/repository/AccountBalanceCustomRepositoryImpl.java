package nl.optiperf.microservices.core.balance.repository;

import nl.optiperf.microservices.core.balance.model.AccountBalance;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Pageable;

@Repository
public class AccountBalanceCustomRepositoryImpl implements AccountBalanceCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<AccountBalance> findFiltered(
            String status,
            String accountType,
            String currency,
            Double minCurrentBalance,
            Double maxCurrentBalance,
            Double minAvailableBalance,
            Double maxAvailableBalance,
            OffsetDateTime lastTransactionAfter,
            OffsetDateTime lastTransactionBefore,
            Pageable pageable) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<AccountBalance> query = cb.createQuery(AccountBalance.class);
        Root<AccountBalance> root = query.from(AccountBalance.class);

        List<Predicate> predicates = new ArrayList<>();

        // Top-level fields
        if (status != null && !status.isEmpty()) predicates.add(cb.equal(root.get("status"), status));
        if (accountType != null && !accountType.isEmpty()) predicates.add(cb.equal(root.get("accountType"), accountType));

        // Embedded balance fields
        Path<Object> balancePath = root.get("balance");

        if (currency != null && !currency.isEmpty()) predicates.add(cb.equal(balancePath.get("currency"), currency));
        if (minCurrentBalance != null) predicates.add(cb.greaterThanOrEqualTo(balancePath.get("currentBalance"), minCurrentBalance));
        if (maxCurrentBalance != null) predicates.add(cb.lessThanOrEqualTo(balancePath.get("currentBalance"), maxCurrentBalance));
        if (minAvailableBalance != null) predicates.add(cb.greaterThanOrEqualTo(balancePath.get("availableBalance"), minAvailableBalance));
        if (maxAvailableBalance != null) predicates.add(cb.lessThanOrEqualTo(balancePath.get("availableBalance"), maxAvailableBalance));
        if (lastTransactionAfter != null) predicates.add(cb.greaterThanOrEqualTo(balancePath.get("lastTransactionDate"), lastTransactionAfter));
        if (lastTransactionBefore != null) predicates.add(cb.lessThanOrEqualTo(balancePath.get("lastTransactionDate"), lastTransactionBefore));

        query.where(predicates.toArray(new Predicate[0]));
        query.orderBy(cb.desc(balancePath.get("lastTransactionDate")));

        return entityManager.createQuery(query)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
    }
}