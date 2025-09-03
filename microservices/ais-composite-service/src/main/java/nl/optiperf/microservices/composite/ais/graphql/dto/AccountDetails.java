package nl.optiperf.microservices.composite.ais.graphql.dto;

public record AccountDetails(
    Long accountNumber,
    String accountName,
    String accountType,
    String currency,
    String status,
    String createdDate
) {
}