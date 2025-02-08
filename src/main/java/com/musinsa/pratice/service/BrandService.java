package com.musinsa.pratice.service;

import com.musinsa.pratice.dto.request.BrandRequestDto;
import com.musinsa.pratice.dto.request.ProductRequestDto;
import com.musinsa.pratice.dto.response.*;
import com.musinsa.pratice.enums.Category;
import com.musinsa.pratice.enums.ErrorCode;
import com.musinsa.pratice.exception.MusinsaException;
import com.musinsa.pratice.mapper.BrandMapper;
import com.musinsa.pratice.mapper.CategoryMapper;
import com.musinsa.pratice.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BrandService {

    private final BrandMapper brandMapper;
    private final ProductMapper productMapper;

    @Transactional
    public void register(BrandRequestDto brandRequestDto) {
        if(brandMapper.existByName(brandRequestDto.name())) {
            throw new MusinsaException(ErrorCode.BRAND_NAME_DUPLICATE);
        }

        try {
            brandMapper.register(brandRequestDto);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Transactional
    public void update(BrandRequestDto brandRequestDto) {
        if(!brandMapper.existById(brandRequestDto.id())) {
            throw new MusinsaException(ErrorCode.BRAND_NOT_FOUND);
        }

        if(brandMapper.existByIdAndName(brandRequestDto)) {
            throw new MusinsaException(ErrorCode.BRAND_NAME_DUPLICATE);
        }

        try {
            brandMapper.update(brandRequestDto);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Transactional
    public void delete(BrandRequestDto brandRequestDto) {
        if(!brandMapper.existById(brandRequestDto.id())) {
            throw new MusinsaException(ErrorCode.BRAND_NOT_FOUND);
        }

        if(productMapper.existByBrandId(brandRequestDto.id())) {
            throw new MusinsaException(ErrorCode.PRODUCT_IS_EXIST);
        }

        try {
            brandMapper.delete(brandRequestDto);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
