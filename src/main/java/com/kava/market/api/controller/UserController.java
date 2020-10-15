package com.kava.market.api.controller;

import com.google.common.collect.ImmutableMap;
import com.kava.market.api.domain.User;
import com.kava.market.api.domain.User.Item;
import com.kava.market.api.model.ItemDto;
import com.kava.market.api.service.ItemService;
import com.kava.market.api.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
@Slf4j
public class UserController {

    private final UserService userService;
    private final ItemService itemService;

    @GetMapping("users/{id}")
    public Mono<User> getUser(@PathVariable String id) {
        return userService.getUser(id);
    }

    @PostMapping("users")
    public Mono<User> createUser(@Validated @RequestBody User user) {
        return userService.saveUser(user);
    }

    @PostMapping("users/{id}/item")
    public Mono<User> createUserItem(@PathVariable String id, @Validated @RequestBody Item item) {
        return userService.createItem(id, item);
    }

    @GetMapping("items")
    public Flux<ItemDto> getItems(
            @RequestParam(required = false) String school,
            @RequestParam(required = false) User.Type type,
            @RequestParam(required = false) User.Subject subject,
            @RequestParam(required = false) String course
    ) {
        val params = new CustomImmutableMapBuilder<String, Object>()
                .putIfValNotNull("school", school)
                .putIfValNotNull("items.type", type)
                .putIfValNotNull("items.subject", subject)
                .putIfValNotNull("items.course", course)
                .build();
        return itemService.findItems(params);
    }

    public static class CustomImmutableMapBuilder<K, V> extends ImmutableMap.Builder<K, V> {

        public CustomImmutableMapBuilder<K, V> putIfValNotNull(K key, V val) {
            if (val != null) super.put(key, val);
            return this;
        }

        @Override
        public CustomImmutableMapBuilder<K, V> put(@NonNull K key, @NonNull V val) {
            super.put(key, val);
            return this;
        }
    }

    @GetMapping("items/{id}")
    public Mono<Item> getItem(@PathVariable String id) {
        return itemService.findItem(id);
    }

    @DeleteMapping("items/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<User> deleteItem(@PathVariable String id) {
        return itemService.deleteItem(id);
    }

}
