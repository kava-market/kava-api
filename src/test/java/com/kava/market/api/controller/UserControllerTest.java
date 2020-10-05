package com.kava.market.api.controller;

import com.kava.market.api.domain.User;
import com.kava.market.api.service.UserService;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
@WebFluxTest
class UserControllerTest {

    @Autowired
    UserController userController;

    @MockBean
    UserService userService;

    @Test
    void getUser() {
        WebTestClient client = WebTestClient.bindToRouterFunction(userController.userRoutes()).build();
        val user = User.builder().id("1").build();

        given(userService.getUser("1")).willReturn(Mono.just(user));

        client.get().uri("/users/1")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(User.class)
                .isEqualTo(user);
    }
}