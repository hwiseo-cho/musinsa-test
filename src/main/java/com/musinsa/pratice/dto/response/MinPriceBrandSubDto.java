package com.musinsa.pratice.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.musinsa.pratice.serializer.IntToMoneySerializer;

import java.util.List;

public record MinPriceBrandSubDto(
        @JsonProperty("브랜드")
        String brand,
        @JsonProperty("카테고리")
        List<ProductSimpleDto> list,
        @JsonProperty("총액")
        @JsonSerialize(using = IntToMoneySerializer.class)
        int totalPrice
) {

}
