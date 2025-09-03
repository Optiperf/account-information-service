package nl.optiperf.microservices.composite.ais.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.WebFilter;

@Configuration
public class GraphQlConfig {

    @Bean
    public WebFilter contextPopulatingFilter() {
        return (exchange, chain) -> {
            String authorizationHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
            return chain.filter(exchange)
                    .contextWrite(context -> {
                        if (authorizationHeader != null) {
                            return context.put("authorizationHeader", authorizationHeader);
                        }
                        return context;
                    });
        };
    }
}
