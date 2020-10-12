package com.kava.market.api.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.Instant;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ItemOperationsImpl implements ItemOperations<User, String> {

    private final ReactiveMongoTemplate mongoTemplate;

    @Override
    public Mono<User> deleteByItemId(String itemId) {
        return mongoTemplate.findAndModify(
                new Query(Criteria.where("items._id").is(itemId)),
                new Update()
                        .pull("items", Query.query(Criteria.where("_id").is(itemId)))
                        .set("updatedOn", Instant.now()),
                options().returnNew(true),
                User.class
        );
    }

    @Override
    public Mono<User> saveItem(String userId, User.Item item) {
        return mongoTemplate.findAndModify(
                new Query(Criteria.where("_id").is(userId)),
                new Update()
                        .push("items", item)
                        .set("updatedOn", Instant.now()),
                options().returnNew(true),
                User.class
        );
    }
}
