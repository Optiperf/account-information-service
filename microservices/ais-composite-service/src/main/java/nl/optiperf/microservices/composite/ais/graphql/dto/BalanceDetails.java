package nl.optiperf.microservices.composite.ais.graphql.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Map;

public record BalanceDetails(
    String currency,
    BigDecimal currentBalance,
    BigDecimal availableBalance,
    String lastTransactionDate
) {
    /**
     * Custom constructor to handle the nested "balance" object in the JSON response
     * from the account-balance-service.
     */
    @JsonCreator
    public BalanceDetails(@JsonProperty("balance") Map<String, Object> balance) {
        this(
            (String) balance.get("currency"),
            new BigDecimal(balance.get("currentBalance").toString()),
            new BigDecimal(balance.get("availableBalance").toString()),
            (String) balance.get("lastTransactionDate")
        );
    }
}
