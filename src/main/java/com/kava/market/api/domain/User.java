package com.kava.market.api.domain;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;
import lombok.With;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.net.URI;
import java.time.Instant;
import java.util.List;

@Value
@Builder
@With
@Document(collection = "users")
public class User {
    @Id
    String id;

    String firstName;

    String lastName;

    String schoolName;

    String email;

    String phone;

    @Singular
    List<Item> items;

    @Value
    @Builder
    @With
    public static class Item {

        @Builder.Default
        @Indexed
        @Id
        String id = new ObjectId().toString();

        String title;

        BigDecimal price;

        Type type;

        Subject subject;

        String course;

        String description;

        URI imageUrl;

        @Builder.Default
        Instant postedOn = Instant.now();
    }

    public enum Type {BOOK, HOMEWORK, EXAM}
    public enum Subject {COMPUTER_SCIENCE, GENDER_STUDIES}

    @CreatedDate
    Instant createdOn;
    @LastModifiedDate
    Instant updatedOn;
}
