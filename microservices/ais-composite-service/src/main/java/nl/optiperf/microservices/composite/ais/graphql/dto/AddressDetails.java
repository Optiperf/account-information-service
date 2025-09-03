package nl.optiperf.microservices.composite.ais.graphql.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

public record AddressDetails(
    String street,
    String houseNumber,
    String city,
    String state,
    String postalCode,
    String country,
    ContactDetails contact
) {
    /**
     * Custom constructor to handle the nested "address" and "contactDetails" objects
     * in the JSON response from the account-address-service.
     */
    @JsonCreator
    public AddressDetails(
        @JsonProperty("address") Map<String, String> address,
        @JsonProperty("contactDetails") Map<String, String> contactDetails
    ) {
        this(
            address.get("street"), address.get("houseNumber"), address.get("city"),
            address.get("state"), address.get("postalCode"), address.get("country"),
            new ContactDetails(contactDetails.get("phone"), contactDetails.get("email"))
        );
    }
}
