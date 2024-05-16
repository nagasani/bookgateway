package com.bookstore.bookgateway.component;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class RemoveDuplicateCorsHeadersFilter extends AbstractGatewayFilterFactory<RemoveDuplicateCorsHeadersFilter.Config> {

    public RemoveDuplicateCorsHeadersFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> chain.filter(exchange).then(Mono.fromRunnable(() -> {
            ServerHttpResponse response = exchange.getResponse();
            if (response.getHeaders().containsKey("Access-Control-Allow-Origin")) {
                // Get the value of Access-Control-Allow-Origin header
                String allowedOrigin = response.getHeaders().get("Access-Control-Allow-Origin").get(0);
                // Remove the existing header
                response.getHeaders().remove("Access-Control-Allow-Origin");
                // Add the header back with only the first value
                response.getHeaders().add("Access-Control-Allow-Origin", allowedOrigin);
            }
        }));
    }

    public static class Config {
        // Configuration properties for the filter can be added here
    }
}
