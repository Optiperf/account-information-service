package nl.optiperf.microservices.composite.ais.graphql.dto;

public record AddressDetails(
    String street,
    String houseNumber,
    String city,
    String state,
    String postalCode,
    String country,
    ContactDetails contact
) {
}
