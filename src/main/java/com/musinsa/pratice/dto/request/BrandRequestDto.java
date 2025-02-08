package com.musinsa.pratice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;

public record BrandRequestDto(int id,
                              @NotBlank(message = "브랜드 이름은 필수 값입니다.")
                              String name
) {
    public BrandRequestDto(int id) {
        this(id, "");
    }
}
