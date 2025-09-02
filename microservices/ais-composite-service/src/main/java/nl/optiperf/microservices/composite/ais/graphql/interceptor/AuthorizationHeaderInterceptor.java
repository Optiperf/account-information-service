package nl.optiperf.microservices.composite.ais.graphql.interceptor;

import org.springframework.graphql.server.WebGraphQlInterceptor;
import org.springframework.graphql.server.WebGraphQlRequest;
import org.springframework.graphql.server.WebGraphQlResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class AuthorizationHeaderInterceptor implements WebGraphQlInterceptor {

    @Override
    public Mono<WebGraphQlResponse> intercept(WebGraphQlRequest request, Chain chain) {
        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        request.configureExecutionInput((executionInput, builder) ->
                builder.graphQLContext(java.util.Collections.singletonMap("authorizationHeader", authHeader)).build());
        return chain.next(request);
    }
}