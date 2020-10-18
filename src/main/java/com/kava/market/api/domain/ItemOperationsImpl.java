package com.kava.market.api.domain;

import com.kava.market.api.model.ItemProjection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Map;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.unwind;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ItemOperationsImpl implements ItemOperations<User, String> {

    private final ReactiveMongoTemplate mongoTemplate;

    @Override
    public Flux<ItemProjection> findItems(Map<String, ?> keyValues) {
        val criteria = new Criteria();
        keyValues.forEach((k, v) -> criteria.and(k).is(v));
        return mongoTemplate.aggregate(
                newAggregation(
                        User.class,
                        unwind("items"),
                        match(criteria)
                ),
                ItemProjection.class);
    }

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
