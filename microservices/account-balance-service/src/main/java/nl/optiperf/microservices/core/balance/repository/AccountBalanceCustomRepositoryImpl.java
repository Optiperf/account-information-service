package nl.optiperf.microservices.core.balance.repository;

import nl.optiperf.microservices.core.balance.model.AccountBalance;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Pageable;

@Repository
public class AccountBalanceCustomRepositoryImpl implements AccountBalanceCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<AccountBalance> findFiltered(Double minCurrentBalance, Double maxCurrentBalance,
                                             Double minAvailableBalance, Double maxAvailableBalance,
                                             Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<AccountBalance> query = cb.createQuery(AccountBalance.class);
        Root<AccountBalance> root = query.from(AccountBalance.class);

        List<Predicate> predicates = new ArrayList<>();

        if (minCurrentBalance != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("balanceDetails").get("currentBalance"), minCurrentBalance));
        }
        if (maxCurrentBalance != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("balanceDetails").get("currentBalance"), maxCurrentBalance));
        }
        if (minAvailableBalance != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("balanceDetails").get("availableBalance"), minAvailableBalance));
        }
        if (maxAvailableBalance != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("balanceDetails").get("availableBalance"), maxAvailableBalance));
        }

        query.where(predicates.toArray(new Predicate[0]));
        query.orderBy(cb.desc(root.get("balanceDetails").get("lastTransactionDate")));

        return entityManager.createQuery(query)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
    }
}