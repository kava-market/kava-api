package com.kava.market.api.service;

import com.kava.market.api.domain.User;
import com.kava.market.api.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class ItemService {
    private final UserRepository userRepository;

    public Mono<User.Item> findItem(String id) {
        return userRepository.findByItems_Id(id)
                .flatMap(user -> Mono.justOrEmpty(user
                        .getItems()
                        .stream()
                        .filter(item -> item.getId().equals(id))
                        .findFirst()))
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found")));
    }


    public Flux<User.Item> findItems(String school, User.Type type, User.Subject subject, String course) {
        return Flux.just(User.Item.builder().build());
    }

    public Mono<User> deleteItem(String id) {
        return userRepository.deleteByItemId(id)
                .log()
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found")));
    }
}
