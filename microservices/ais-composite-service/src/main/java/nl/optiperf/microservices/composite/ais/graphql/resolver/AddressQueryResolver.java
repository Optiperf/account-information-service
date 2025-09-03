package nl.optiperf.microservices.composite.ais.graphql.resolver;

import nl.optiperf.microservices.composite.ais.graphql.client.AddressDetailsClient;
import nl.optiperf.microservices.composite.ais.graphql.dto.AddressDetails;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
public class AddressQueryResolver {

    private final AddressDetailsClient addressDetailsClient;

    public AddressQueryResolver(AddressDetailsClient addressDetailsClient) {
        this.addressDetailsClient = addressDetailsClient;
    }

    @QueryMapping
    public Mono<AddressDetails> address(@Argument Long accountNumber, @RequestHeader(value = "Authorization", required = false) String authorizationHeader) {
        return addressDetailsClient.getAddressDetails(accountNumber, authorizationHeader);
    }
}
