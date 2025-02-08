package com.musinsa.pratice.dto.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.musinsa.pratice.enums.Category;
import com.musinsa.pratice.mapper.ProductMapper;
import com.musinsa.pratice.serializer.IntToMoneySerializer;

public record ProductDto(String categoryName,
                         String brand,
                         @JsonSerialize(using = IntToMoneySerializer.class) int price
) {

}
