package com.kava.market.api.controller;

import com.kava.market.api.domain.User;
import com.kava.market.api.service.ItemService;
import com.kava.market.api.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
@WebFluxTest(UserController.class)
class UserControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    UserService userService;

    @MockBean
    ItemService itemService;

    User user;
    User.Item item;
    {
        item = User.Item.builder().id("11").build();
        user = User.builder().id("1").item(item).build();
    }

    @Test
    void getUser() {
        given(userService.getUser("1")).willReturn(Mono.just(user));
        webTestClient.get()
                .uri("/api/users/1")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(User.class)
                .isEqualTo(user);
    }

    @Test
    void createUser() {
        given(userService.saveUser(any())).willReturn(Mono.just(user));
        webTestClient.post()
                .uri("/api/users/")
                .bodyValue(user)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(User.class)
                .isEqualTo(user);
    }

    @Test
    void createUserItem() {
        given(userService.createItem(any(), any())).willReturn(Mono.just(user));
        webTestClient.post()
                .uri("/api/users/{id}/item", "1")
                .bodyValue(user)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(User.class)
                .isEqualTo(user);
    }

    @Test
    void getItems() {

    }

    @Test
    void getItem() {
        given(itemService.findItem(any())).willReturn(Mono.just(item));
        webTestClient.get()
                .uri("/api/items/{id}", "11")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(User.Item.class)
                .isEqualTo(item);
    }

    @Test
    void deleteItem() {
        given(itemService.deleteItem(any())).willReturn(Mono.just(user));
        webTestClient.delete()
                .uri("/api/items/{id}", "11")
                .exchange()
                .expectStatus()
                .isNoContent()
                .expectBody(User.class)
                .isEqualTo(user);
    }
}