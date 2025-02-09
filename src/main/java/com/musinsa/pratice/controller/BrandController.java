package com.musinsa.pratice.controller;

import com.musinsa.pratice.dto.request.BrandRequestDto;
import com.musinsa.pratice.dto.request.ProductRequestDto;
import com.musinsa.pratice.dto.response.*;
import com.musinsa.pratice.service.BrandService;
import com.musinsa.pratice.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BrandController {

    private static final String SUCCESS_MSG = "SUCCESS";
    private final BrandService brandService;

    /**
     * 구현 4-2) 브랜드 추가 / 업데이트 / 삭제하는 API
     */
    @PostMapping("/brand")
    public CommonResponse<?> register(@Valid @RequestBody BrandRequestDto brandRequestDto) {

        brandService.register(brandRequestDto);

        return new CommonResponse<>(HttpStatus.CREATED, SUCCESS_MSG);
    }

    @PatchMapping("/brand")
    public CommonResponse<?> update(@Valid @RequestBody BrandRequestDto brandRequestDto) {

        brandService.update(brandRequestDto);

        return new CommonResponse<>(HttpStatus.OK, SUCCESS_MSG);
    }

    @DeleteMapping("/brand")
    public CommonResponse<?> delete(@RequestBody BrandRequestDto brandRequestDto) {

        brandService.delete(brandRequestDto);

        return new CommonResponse<>(HttpStatus.OK, SUCCESS_MSG);
    }
}
