package com.kava.market.api.controller;

import com.kava.market.api.domain.User;
import com.kava.market.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Bean
    public RouterFunction<ServerResponse> userRoutes() {
        return route()
                .GET("/users/{id}", this::getUser)
                .POST("/users", this::createUser)
//                .DELETE("/users/{id}", this::deleteUser)
                .build();
    }

    private Mono<ServerResponse> getUser(ServerRequest serverRequest) {
        return userService.getUser(serverRequest.pathVariable("id"))
                .flatMap(user -> ServerResponse.ok().body(user, User.class));
    }

    private Mono<ServerResponse> createUser(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(User.class)
                .flatMap(userService::saveUser)
                .flatMap(user -> ServerResponse.ok().build());
    }

}
