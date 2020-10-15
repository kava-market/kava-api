package com.kava.market.api.model;

import lombok.Value;
import lombok.With;

@Value(staticConstructor = "of")
@With
public class ItemDto {

    String title;

    String school;

    String course;

}
