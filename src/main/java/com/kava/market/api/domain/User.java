package com.kava.market.api.domain;

import lombok.Builder;
import lombok.Value;
import lombok.With;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.net.URI;
import java.time.Instant;
import java.util.List;

@Value
@Builder
@With
public class User {
    @Id
    String id;

    String firstName;

    String lastName;

    String schoolName;

    String email;

    String phone;

    List<Item> items;

    enum Type {BOOK, HOMEWORK, EXAM}

    @Value
    @Builder
    @With
    public static class Item {
        String id;
        String title;
        BigDecimal price;
        Type type;
        String description;
        URI imageUrl;
    }

    @CreatedDate
    Instant createdOn;
    @LastModifiedDate
    Instant updatedOn;
}
