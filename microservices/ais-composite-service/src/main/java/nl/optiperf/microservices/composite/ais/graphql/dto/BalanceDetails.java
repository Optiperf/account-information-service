package nl.optiperf.microservices.composite.ais.graphql.dto;

import java.math.BigDecimal;

public record BalanceDetails(
    String currency,
    BigDecimal currentBalance,
    BigDecimal availableBalance,
    String lastTransactionDate
) {
}
