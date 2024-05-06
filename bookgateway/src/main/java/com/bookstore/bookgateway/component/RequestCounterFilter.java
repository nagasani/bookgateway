package com.bookstore.bookgateway.component;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import reactor.core.publisher.Mono;

@Component
public class RequestCounterFilter implements WebFilter {

    private final MeterRegistry meterRegistry;
    private Counter requestCounter;

    public RequestCounterFilter(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @PostConstruct
    public void init() {
        requestCounter = this.meterRegistry.counter("api_requests_total", "endpoint", "api_gateway");
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        requestCounter.increment();
        return chain.filter(exchange);
    }
}
