package nl.optiperf.microservices.composite.ais.graphql.resolver;

import nl.optiperf.microservices.composite.ais.graphql.client.AddressDetailsClient;
import nl.optiperf.microservices.composite.ais.graphql.client.BalanceDetailsClient;
import nl.optiperf.microservices.composite.ais.graphql.dto.AccountDetails;
import nl.optiperf.microservices.composite.ais.graphql.dto.AddressDetails;
import nl.optiperf.microservices.composite.ais.graphql.dto.BalanceDetails;
import nl.optiperf.microservices.composite.ais.graphql.dto.ContactDetails;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import reactor.core.publisher.Mono;

@Controller
public class AccountResolver {

    private final BalanceDetailsClient balanceDetailsClient;
    private final AddressDetailsClient addressDetailsClient;

    public AccountResolver(BalanceDetailsClient balanceDetailsClient, AddressDetailsClient addressDetailsClient) {
        this.balanceDetailsClient = balanceDetailsClient;
        this.addressDetailsClient = addressDetailsClient;
    }

    @SchemaMapping
    public Mono<BalanceDetails> balance(AccountDetails accountDetails, @RequestHeader(value = "Authorization", required = false) String authorizationHeader) {
        return balanceDetailsClient.getBalanceDetails(accountDetails.accountNumber(), authorizationHeader);
    }

    @SchemaMapping
    public Mono<AddressDetails> address(AccountDetails accountDetails, @RequestHeader(value = "Authorization", required = false) String authorizationHeader) {
        return addressDetailsClient.getAddressDetails(accountDetails.accountNumber(), authorizationHeader);
    }

}
