package com.jayant.api_gateway;


import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import org.springframework.web.server.ServerWebExchange;    //The reactive equivalent of HttpServletRequest + HttpServletResponse


import reactor.core.publisher.Mono;

@Component
@Slf4j         // For logging
public class GlobalLoggingFilter implements GlobalFilter, Ordered {

    //Ordered-> lets you control the execution order of multiple filters

    //Chain contains the whole chain inside the gateway filter chain
    //In exchange we get the information about the requests and response for this particular requests
    //Why the return type of Mono void
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        //Mono<Void> means: "this operation completes asynchronously and produces no return value.

//        pre-filter
        log.info("Logging from Global Pre: {}", exchange.getRequest().getURI());

        //then - run after the filter
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            log.info("Logging from Global Post: {}", exchange.getResponse().getStatusCode());
        }));
    }

    @Override
    public int getOrder() {
        return 5;
    }
}
