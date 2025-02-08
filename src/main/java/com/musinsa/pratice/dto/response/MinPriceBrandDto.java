package com.musinsa.pratice.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MinPriceBrandDto(@JsonProperty("최저가") MinPriceBrandSubDto minPriceBrandSubDto) {

}
