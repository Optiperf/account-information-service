package nl.optiperf.microservices.composite.ais.graphql.client;

import nl.optiperf.microservices.composite.ais.graphql.dto.AddressDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component; 
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class AddressDetailsClient {

    private final WebClient webClient;

    public AddressDetailsClient(
            WebClient.Builder builder,
            @Value("${service.url.address-details}") String addressDetailsServiceUrl
    ) {
        this.webClient = builder.baseUrl(addressDetailsServiceUrl).build();
    }

    public Mono<AddressDetails> getAddressDetails(Long accountNumber, String authorizationHeader) {
        WebClient.RequestHeadersSpec<?> spec = webClient.get()
                .uri("/account-address/{accountNumber}", accountNumber);

        if (StringUtils.hasText(authorizationHeader)) {
            spec.header("Authorization", authorizationHeader);
        }

        return spec.retrieve().bodyToMono(AddressDetails.class);
    }
}
