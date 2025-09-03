package nl.optiperf.microservices.composite.ais.graphql.controller;

import nl.optiperf.microservices.composite.ais.graphql.client.AccountDetailsClient;
import nl.optiperf.microservices.composite.ais.graphql.dto.AccountDetails;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
public class AisCompositeServiceApplicationController {

    private final AccountDetailsClient accountDetailsClient;

    public AisCompositeServiceApplicationController(AccountDetailsClient accountDetailsClient) {
        this.accountDetailsClient = accountDetailsClient;
    }
}
