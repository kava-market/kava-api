package com.kava.market.api.domain;

import com.kava.market.api.model.ItemProjection;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

public interface ItemOperations<T extends User, ID> {

    Flux<? extends ItemProjection> findItems(Map<String, ?> keyValues);

    Mono<T> deleteByItemId(ID id);

    Mono<T> saveItem(ID id, User.Item item);
}
