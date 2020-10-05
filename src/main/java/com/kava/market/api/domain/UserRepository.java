package com.kava.market.api.domain;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface UserRepository extends ReactiveMongoRepository<User, String> {

    Flux<User> findBySchoolName(String schoolName);
}
