package nl.optiperf.microservices.core.account.repository;

import nl.optiperf.microservices.core.account.model.AccountDetails;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Pageable;

@Repository
public class AccountDetailsCustomRepositoryImpl implements AccountDetailsCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<AccountDetails> findFiltered(String status, AccountDetails.AccountType accountType, String currency,
                                             OffsetDateTime createdAfter, OffsetDateTime createdBefore, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<AccountDetails> query = cb.createQuery(AccountDetails.class);
        Root<AccountDetails> root = query.from(AccountDetails.class);

        List<Predicate> predicates = new ArrayList<>();

        if (status != null && !status.isEmpty()) predicates.add(cb.equal(root.get("status"), status));
        if (accountType != null) predicates.add(cb.equal(root.get("accountType"), accountType));
        if (currency != null && !currency.isEmpty()) predicates.add(cb.equal(root.get("currency"), currency));
        if (createdAfter != null) predicates.add(cb.greaterThanOrEqualTo(root.get("createdDate"), createdAfter));
        if (createdBefore != null) predicates.add(cb.lessThanOrEqualTo(root.get("createdDate"), createdBefore));

        query.where(predicates.toArray(new Predicate[0]));
        query.orderBy(cb.desc(root.get("createdDate")));

        return entityManager.createQuery(query)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
    }
}