package nl.optiperf.microservices.composite.ais.graphql.client;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import nl.optiperf.microservices.composite.ais.graphql.dto.AccountDetails;

@Service
public class AccountDetailsClient {
    private final WebClient webClient;

    public AccountDetailsClient(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://kong:8000").build();
    }

    public Mono<AccountDetails> getAccountDetails(Long accountNumber, String authorizationHeader) {
        WebClient.RequestHeadersSpec<?> spec = webClient.get()
            .uri("/account-details/{id}", accountNumber);

        if (StringUtils.hasText(authorizationHeader)) {
            spec.header("Authorization", authorizationHeader);
        }

        return spec.retrieve()
                .bodyToMono(AccountDetails.class);
    }
}