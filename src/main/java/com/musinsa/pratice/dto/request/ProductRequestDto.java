package com.musinsa.pratice.dto.request;

import jakarta.validation.constraints.NotNull;

public record ProductRequestDto(int id, int categoryId, int brandId, int price) {

    public ProductRequestDto(int categoryId, int brandId, int price) {
        this(0,categoryId, brandId, price);
    }

    public ProductRequestDto(int id) {
        this(id, 0, 0, 0);
    }
}
