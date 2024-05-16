package com.bookstore.bookgateway.component;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class LogRequestHeadersFilter extends AbstractGatewayFilterFactory<LogRequestHeadersFilter.Config> {

    public LogRequestHeadersFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            // Log request headers
            request.getHeaders().forEach((name, values) -> {
                values.forEach(value -> {
                    System.out.println(name + ": " + value);
                });
            });

            // Proceed with the next filter in the chain
            return chain.filter(exchange);
        };
    }

    public static class Config {
        // Put the configuration properties here
    }
}
