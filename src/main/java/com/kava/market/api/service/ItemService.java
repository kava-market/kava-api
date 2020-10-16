package com.kava.market.api.service;

import com.kava.market.api.domain.User;
import com.kava.market.api.domain.UserRepository;
import com.kava.market.api.model.ItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

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

    public Flux<ItemDto> findItems(Map<String, Object> kvs) {
        return userRepository.findItems(kvs)
                .map(item -> ItemDto.of(item.getTitle(), item.getSchool(), item.getCourse())); // TODO representation model on hold
    }

    public Mono<User> deleteItem(String id) {
        return userRepository.deleteByItemId(id)
                .log()
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found")));
    }
}
