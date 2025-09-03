package nl.optiperf.microservices.composite.ais.graphql.resolver;

import nl.optiperf.microservices.composite.ais.graphql.client.AccountDetailsClient;
import nl.optiperf.microservices.composite.ais.graphql.dto.AccountDetails;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.ContextValue;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Optional;

@Controller
public class AccountQueryResolver {

    private final AccountDetailsClient accountDetailsClient;

    public AccountQueryResolver(AccountDetailsClient accountDetailsClient) {
        this.accountDetailsClient = accountDetailsClient;
    }

    @QueryMapping
    public Flux<AccountDetails> accounts(@Argument List<Long> accountNumbers, @ContextValue Optional<String> authorizationHeader) {
        return Flux.fromIterable(accountNumbers)
            .flatMap(accountNumber ->
                accountDetailsClient.getAccountDetails(
                    accountNumber,
                    authorizationHeader.orElse(null)
                ));
    }
}
