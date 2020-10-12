package com.kava.market.api.domain;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<User, String>, ItemOperations<User, String> {

    Flux<User> findBySchoolName(String schoolName);

    Mono<User> findByItems_Id(String id);
}
