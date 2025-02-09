package com.musinsa.pratice.controller;

import com.musinsa.pratice.dto.request.ProductRequestDto;
import com.musinsa.pratice.dto.response.*;
import com.musinsa.pratice.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private static final String SUCCESS_MSG = "SUCCESS";
    private final ProductService productService;

    /**
     * 구현 1) 카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회하는 API
     */
    @GetMapping("/product/min-price")
    public CommonResponse<MinPriceDto> minPrice() {
        MinPriceDto minPrice = productService.minPrice();

        return new CommonResponse<>(HttpStatus.OK, SUCCESS_MSG, minPrice);
    }

    /**
     * 구현 2) 단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의
     *        상품 가격, 총액을 조회하는 API
     */
    @GetMapping("/product/brand/min-price")
    public CommonResponse<MinPriceBrandDto> minPriceBrand() {
        MinPriceBrandSubDto minPriceBrandSubDto = productService.minPriceBrand();

        return new CommonResponse<>(HttpStatus.OK, SUCCESS_MSG, new MinPriceBrandDto(
                minPriceBrandSubDto
        ));
    }

    /**
     * 구현 3) 카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회하는 API
     */
    @GetMapping("/product/{categoryName}/extremes-price")
    public CommonResponse<ExtremesPriceDto> extremesPrice(@PathVariable(name = "categoryName") String categoryName) {

        ExtremesPriceDto extremesPriceDto = productService.extremesPrice(categoryName);

        return new CommonResponse<>(HttpStatus.OK, SUCCESS_MSG, extremesPriceDto);
    }

    /**
     * 구현 4-1) 상품을 추가 / 업데이트 / 삭제하는 API
     */
    @PostMapping("/product")
    public CommonResponse<?> register(@RequestBody ProductRequestDto productRequestDto) {

        productService.register(productRequestDto);

        return new CommonResponse<>(HttpStatus.CREATED, SUCCESS_MSG);
    }

    @PatchMapping("/product")
    public CommonResponse<?> update(@RequestBody ProductRequestDto productRequestDto) {

        productService.update(productRequestDto);

        return new CommonResponse<>(HttpStatus.OK, SUCCESS_MSG);
    }

    @DeleteMapping("/product")
    public CommonResponse<?> delete(@RequestBody ProductRequestDto productRequestDto) {

        productService.delete(productRequestDto);

        return new CommonResponse<>(HttpStatus.OK, SUCCESS_MSG);
    }
}
