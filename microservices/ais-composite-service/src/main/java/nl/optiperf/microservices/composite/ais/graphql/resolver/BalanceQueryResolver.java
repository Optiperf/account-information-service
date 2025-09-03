package nl.optiperf.microservices.composite.ais.graphql.resolver;

import nl.optiperf.microservices.composite.ais.graphql.client.BalanceDetailsClient;
import nl.optiperf.microservices.composite.ais.graphql.dto.BalanceDetails;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
public class BalanceQueryResolver {

    private final BalanceDetailsClient balanceDetailsClient;

    public BalanceQueryResolver(BalanceDetailsClient balanceDetailsClient) {
        this.balanceDetailsClient = balanceDetailsClient;
    }

    @QueryMapping
    public Mono<BalanceDetails> balance(@Argument Long accountNumber, @RequestHeader(value = "Authorization", required = false) String authorizationHeader) {
        return balanceDetailsClient.getBalanceDetails(accountNumber, authorizationHeader);
    }
}
