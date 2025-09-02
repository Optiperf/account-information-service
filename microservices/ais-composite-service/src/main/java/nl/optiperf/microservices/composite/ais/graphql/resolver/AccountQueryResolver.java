package nl.optiperf.microservices.composite.ais.graphql.resolver;

import graphql.schema.DataFetchingEnvironment;
import nl.optiperf.microservices.composite.ais.graphql.client.AccountDetailsClient;
import nl.optiperf.microservices.composite.ais.graphql.dto.AccountDetails;
import org.springframework.http.HttpHeaders;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.stereotype.Controller;

import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

import java.util.List;

@Controller
public class AccountQueryResolver {

    private final AccountDetailsClient accountDetailsClient;

    public AccountQueryResolver(AccountDetailsClient accountDetailsClient) {
        this.accountDetailsClient = accountDetailsClient;
    }

    @QueryMapping
    public Mono<AccountDetails> account(@Argument Long accountNumber, DataFetchingEnvironment dfe) {
        String authorizationHeader = dfe.getGraphQlContext().get("authorizationHeader");
        return accountDetailsClient.getAccountDetails(accountNumber, authorizationHeader);
    }

    @QueryMapping
    public Flux<AccountDetails> accountsByIds(@Argument List<Long> accountNumbers, DataFetchingEnvironment dfe) {
        String authorizationHeader = dfe.getGraphQlContext().get("authorizationHeader");
        return Flux.fromIterable(accountNumbers)
                .flatMap(accountNumber -> accountDetailsClient.getAccountDetails(accountNumber, authorizationHeader));
    }
}
