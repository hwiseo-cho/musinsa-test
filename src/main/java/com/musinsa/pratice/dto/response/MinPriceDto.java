package com.musinsa.pratice.dto.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.musinsa.pratice.serializer.IntToMoneySerializer;

import java.util.List;

public record MinPriceDto(List<ProductDto> list,
                          @JsonSerialize(using = IntToMoneySerializer.class) int totalPrice
) {

}
