package com.kava.market.api.service;

import com.kava.market.api.domain.User;
import com.kava.market.api.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.kava.market.api.domain.User.Item;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public Mono<User> getUser(String id) {
        return userRepository.findById(id);
    }

    public Mono<User> saveUser(User user) {
        return userRepository.save(user);
    }

    public Mono<User> createItem(String id, Item item) {
        return userRepository.saveItem(id, item);
    }
}
