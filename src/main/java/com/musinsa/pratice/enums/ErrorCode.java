package com.musinsa.pratice.enums;

import lombok.Getter;

@Getter
public enum ErrorCode {
    PRODUCT_NOT_FOUND("E001", "해당 상품을 찾을 수 없습니다."),
    CATEGORY_NOT_FOUND("E002", "해당 카테고리를 찾을 수 없습니다."),
    BRAND_NOT_FOUND("E003", "해당 브랜드를 찾을 수 없습니다."),
    BRAND_NAME_DUPLICATE("E004", "중복되는 이름의 브랜드가 존재합니다."),
    PRODUCT_IS_EXIST("E005","해당 브랜드의 상품이 아직 존재합니다."),
    ;

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
