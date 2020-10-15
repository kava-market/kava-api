package com.kava.market.api.domain;

import reactor.core.publisher.Mono;

public interface ItemOperations<T extends User, ID> {

    Mono<T> deleteByItemId(ID id);

    Mono<T> saveItem(ID id, User.Item item);
}
