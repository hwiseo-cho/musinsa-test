package com.musinsa.pratice.service;

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
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductMapper productMapper;
    private final BrandMapper brandMapper;
    private final CategoryMapper categoryMapper;

    public MinPriceDto minPrice() {
        List<ProductDto> list = null;

        try {
            list = productMapper.minPriceList();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return new MinPriceDto(
                list,
                list.stream().mapToInt(ProductDto::price).sum()
        );
    }

    public MinPriceBrandSubDto minPriceBrand() {
        List<ProductDto> list = null;

        try {
            list = productMapper.minPriceBrandProductList();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return new MinPriceBrandSubDto(
                list.stream().map(ProductDto::brand).findAny().orElse(""),
                list.stream().map(p -> new ProductSimpleDto(
                        p.categoryName(),
                        p.price()
                )).collect(Collectors.toList()),
                list.stream().mapToInt(ProductDto::price).sum()
        );
    }

    public ExtremesPriceDto extremesPrice(String categoryName) {
        List<ExtremesPriceSubDto> list = null;

        try {
            list = productMapper.extremesPrice(categoryName);

            if(list.isEmpty()) {
                throw new MusinsaException(ErrorCode.CATEGORY_NOT_FOUND);
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return new ExtremesPriceDto(
                Category.valueOf(categoryName).label,
                list.get(0),
                list.get(1)
        );
    }

    @Transactional
    public void register(ProductRequestDto productRequestDto) {
        if(!categoryMapper.existById(productRequestDto.categoryId())) {
            throw new MusinsaException(ErrorCode.CATEGORY_NOT_FOUND);
        }

        if(!brandMapper.existById(productRequestDto.brandId())) {
            throw new MusinsaException(ErrorCode.BRAND_NOT_FOUND);
        }

        try {
            productMapper.register(productRequestDto);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Transactional
    public void update(ProductRequestDto productRequestDto) {
        if(!productMapper.existById(productRequestDto.id())) {
            throw new MusinsaException(ErrorCode.PRODUCT_NOT_FOUND);
        }

        try {
            productMapper.update(productRequestDto);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Transactional
    public void delete(ProductRequestDto productRequestDto) {
        if(!productMapper.existById(productRequestDto.id())) {
            throw new MusinsaException(ErrorCode.PRODUCT_NOT_FOUND);
        }

        try {
            productMapper.delete(productRequestDto);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
