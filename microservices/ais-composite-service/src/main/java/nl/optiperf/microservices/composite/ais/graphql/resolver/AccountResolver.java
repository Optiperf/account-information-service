package nl.optiperf.microservices.composite.ais.graphql.resolver;

import nl.optiperf.microservices.composite.ais.graphql.client.AddressDetailsClient;
import nl.optiperf.microservices.composite.ais.graphql.client.BalanceDetailsClient;
import nl.optiperf.microservices.composite.ais.graphql.dto.AccountDetails;
import nl.optiperf.microservices.composite.ais.graphql.dto.AddressDetails;
import nl.optiperf.microservices.composite.ais.graphql.dto.BalanceDetails;
import org.springframework.graphql.data.method.annotation.ContextValue;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;
import java.util.Optional;

@Controller
public class AccountResolver {

    private final BalanceDetailsClient balanceDetailsClient;
    private final AddressDetailsClient addressDetailsClient;

    public AccountResolver(BalanceDetailsClient balanceDetailsClient, AddressDetailsClient addressDetailsClient) {
        this.balanceDetailsClient = balanceDetailsClient;
        this.addressDetailsClient = addressDetailsClient;
    }

    @SchemaMapping
    public Mono<BalanceDetails> balance(AccountDetails accountDetails, @ContextValue Optional<String> authorizationHeader) {
        return balanceDetailsClient.getBalanceDetails(accountDetails.accountNumber(), authorizationHeader.orElse(null));
    }

    @SchemaMapping
    public Mono<AddressDetails> address(AccountDetails accountDetails, @ContextValue Optional<String> authorizationHeader) {
        return addressDetailsClient.getAddressDetails(accountDetails.accountNumber(), authorizationHeader.orElse(null));
    }

}
