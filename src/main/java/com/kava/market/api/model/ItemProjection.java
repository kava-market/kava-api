package com.kava.market.api.model;

import com.kava.market.api.domain.User;
import lombok.Builder;
import lombok.Value;
import org.springframework.data.mongodb.core.index.Indexed;

import java.math.BigDecimal;
import java.net.URI;
import java.time.Instant;

@Value
public class ItemProjection {

    String school;

    String title;

    BigDecimal price;

    User.Type type;

    User.Subject subject;

    String course;

    String description;

    URI imageUrl;

    Instant postedOn;
}
