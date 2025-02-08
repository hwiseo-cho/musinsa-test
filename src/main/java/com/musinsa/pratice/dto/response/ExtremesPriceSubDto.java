package com.musinsa.pratice.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.musinsa.pratice.serializer.IntToMoneySerializer;

public record ExtremesPriceSubDto(
        @JsonProperty("브랜드")
        String brand,
        @JsonProperty("가격")
        @JsonSerialize(using = IntToMoneySerializer.class)
        int price
) {

}
