package com.musinsa.pratice.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.musinsa.pratice.serializer.IntToMoneySerializer;
import com.musinsa.pratice.serializer.IntToMoneyDeserializer;


import java.io.Serializable;
import java.util.List;

public record MinPriceDto(List<ProductDto> list,
                          @JsonSerialize(using = IntToMoneySerializer.class)
                          @JsonDeserialize(using = IntToMoneyDeserializer.class)
                          int totalPrice
) {

}
