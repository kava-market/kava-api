package com.kava.market.api.service;

import com.kava.market.api.domain.User;
import com.kava.market.api.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

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
}
