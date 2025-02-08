package com.musinsa.pratice.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ExtremesPriceDto(
        @JsonProperty("카테고리")
        String categoryName,
        @JsonProperty("최저가")
        ExtremesPriceSubDto minPrice,
        @JsonProperty("최고가")
        ExtremesPriceSubDto maxPrice
) {

}
