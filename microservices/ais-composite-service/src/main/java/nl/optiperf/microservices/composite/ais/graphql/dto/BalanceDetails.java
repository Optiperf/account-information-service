package nl.optiperf.microservices.composite.ais.graphql.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Map;

public record BalanceDetails(
    BigDecimal currentBalance,
    BigDecimal availableBalance,
    String lastTransactionDate
) {
    /**
     * Custom constructor to handle the nested "balanceDetails" object in the JSON response
     * from the account-balance-service.
     */
    @JsonCreator
    public BalanceDetails(@JsonProperty("balanceDetails") Map<String, Object> balanceDetails) {
        this(
            balanceDetails != null ? new BigDecimal(balanceDetails.get("currentBalance").toString()) : null,
            balanceDetails != null ? new BigDecimal(balanceDetails.get("availableBalance").toString()) : null,
            balanceDetails != null ? (String) balanceDetails.get("lastTransactionDate") : null
        );
    }
}
