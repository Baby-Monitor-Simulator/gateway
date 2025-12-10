package com.babymonitor.gateway_api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.gateway.route.RouteLocator;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(classes = GatewayApiApplication.class)
class ChangeRouteConfigTest {

    @Autowired
    private RouteLocator routeLocator;

    @Test
    void testDataRouteExists() {
        StepVerifier.create(
            routeLocator.getRoutes()
                .filter(route -> route.getId().equals("data"))
        )
        .expectNextMatches(route -> 
            //route.getUri().toString().contains("dataService:8080")
            route.getUri().toString().contains("localhost:8722")
        )
        .expectComplete()
        .verify();
    }

    @Test
    void testChangeRouteExists() {
        StepVerifier.create(
            routeLocator.getRoutes()
                .filter(route -> route.getId().equals("change"))
        )
        .expectNextMatches(route -> 
            route.getUri().toString().contains("changeService:8082")
        )
        .expectComplete()
        .verify();
    }

    @Test
    void testScenarioRouteExists() {
        StepVerifier.create(
            routeLocator.getRoutes()
                .filter(route -> route.getId().equals("scenario"))
        )
        .expectNextMatches(route -> 
            route.getUri().toString().contains("scenarioService:8083")
        )
        .expectComplete()
        .verify();
    }
}