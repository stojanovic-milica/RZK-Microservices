package com.rzk.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfig {

    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
        return builder.routes()

                .route("korisnik-service", r -> r.path("/api/korisnik/**")
                        .uri("lb://korisnik-service"))


                .route("edukacija-service", r -> r.path("/api/edukacija/**")
                        .uri("lb://edukacija-service"))



                .route("home", r -> r.path("/home")
                        .filters(f -> f.rewritePath("/home", "/api/usluga/sveUsluge")
                        )
                        .uri("lb://termin-service"))

//                .route("home-ruta", r -> r.path("/home")
//                        .filters(f -> f.setPath("/api/usluga/sveUsluge"))
//                        .uri("lb://termin-service"))


                .route("termin-service", r -> r.path("/api/termin/**")
                        .uri("lb://termin-service"))

                .route("termin-service", r -> r.path("/api/usluga/**")
                        .uri("lb://termin-service"))

                .route("termin-service", r -> r.path("/api/recenzija/**")
                        .uri("lb://termin-service"))


                .route("placanje-service", r -> r.path("/api/racun/**")
                        .uri("lb://placanje-service"))



                .build();
    }
}