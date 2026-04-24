package com.jayant.api_gateway;

import com.jayant.api_gateway.service.JwtService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

@Component
@Slf4j
//Spring strips GatewayFilterFactory from the class name to derive the YAML filter name.
public class AuthenticationGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthenticationGatewayFilterFactory.Config> {

    private final JwtService jwtService;

    public AuthenticationGatewayFilterFactory(JwtService jwtService) {
        super(Config.class);
        this.jwtService = jwtService;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {

            if(!config.isEnabled) return chain.filter(exchange);

            String authorizationHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
            if (authorizationHeader == null) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
            // We can also add a check if the header starts with Bearer or not
            String token = authorizationHeader.split("Bearer ")[1];

            Long userId = jwtService.getUserIdFromToken(token);

            //Since requests are immutable in reactive Spring (you can't just call setHeader()),
            // you use .mutate() to create a modified copy of the request.

            ServerWebExchange mutatedExchange = exchange.mutate()
                    .request(exchange.getRequest().mutate()
                            .header("X-User-Id", userId.toString())
                            .build())
                    .build();
            return chain.filter(mutatedExchange); // pass the mutated exchange

        };
    }

    @Data
    public static class Config {
        private boolean isEnabled;
    }
}
