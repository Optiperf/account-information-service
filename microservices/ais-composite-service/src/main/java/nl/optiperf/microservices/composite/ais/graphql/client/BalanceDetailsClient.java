package nl.optiperf.microservices.composite.ais.graphql.client;

import nl.optiperf.microservices.composite.ais.graphql.dto.BalanceDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component; 
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class BalanceDetailsClient {

    private final WebClient webClient;

    public BalanceDetailsClient(
            WebClient.Builder builder,
            @Value("${service.url.balance-details}") String balanceDetailsServiceUrl
    ) {
        this.webClient = builder.baseUrl(balanceDetailsServiceUrl).build();
    }

    public Mono<BalanceDetails> getBalanceDetails(Long accountNumber, String authorizationHeader) {
        WebClient.RequestHeadersSpec<?> spec = webClient.get()
                .uri("/account-balance/{accountNumber}", accountNumber);

        if (StringUtils.hasText(authorizationHeader)) {
            spec.header("Authorization", authorizationHeader);
        }

        return spec.retrieve().bodyToMono(BalanceDetails.class);
    }
}
