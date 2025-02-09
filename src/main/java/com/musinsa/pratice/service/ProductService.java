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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductMapper productMapper;
    private final BrandMapper brandMapper;
    private final CategoryMapper categoryMapper;

    @Cacheable(value = "minPrice")
    public MinPriceDto minPrice() {
        List<ProductDto> list = null;

        try {
            list = productMapper.findAll();

            // 카테고리별 최저가 상품 찾기
            Map<String, Optional<ProductDto>> minPriceProductsByCategory = list.stream()
                    .collect(Collectors.groupingBy(
                            ProductDto::categoryName,
                            Collectors.minBy(Comparator.comparingInt(ProductDto::price))
                    ));

            list = minPriceProductsByCategory.values().stream()
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .sorted(Comparator.comparing(p -> Category.of(p.categoryName()).ordinal()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return new MinPriceDto(
                list,
                list.stream().mapToInt(ProductDto::price).sum()
        );
    }

    @Cacheable(value = "minPriceBrand")
    public MinPriceBrandSubDto minPriceBrand() {
        List<ProductDto> list = null;
        List<ProductSimpleDto> productSimpleDtoList = null;
        String brand = null;

        try {
            list = productMapper.findAll();

            // 브랜드별 총 가격 계산
            Map<String, Integer> brandTotalPrices = list.stream()
                    .collect(Collectors.groupingBy(ProductDto::brand, Collectors.summingInt(ProductDto::price)));

            // 최소 가격 브랜드 찾기
            brand = brandTotalPrices.entrySet().stream()
                    .min(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey).orElse(null);

            String finalBrand = brand;
            productSimpleDtoList = list.stream().filter(p -> p.brand().equals(finalBrand)).map(p -> new ProductSimpleDto(
                    p.categoryName(),
                    p.price()
            )).collect(Collectors.toList());

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return new MinPriceBrandSubDto(
                brand,
                productSimpleDtoList,
                productSimpleDtoList.stream().mapToInt(ProductSimpleDto::price).sum()
        );
    }

    @Cacheable(value = "extremesPrice", key = "#categoryName")
    public ExtremesPriceDto extremesPrice(String categoryName) {
        List<ExtremesPriceSubDto> list = null;
        ExtremesPriceSubDto minExtremesPriceSubDto = null;
        ExtremesPriceSubDto maxExtremesPriceSubDto = null;

        try {
            list = productMapper.extremesPrice(categoryName);

            if(list.isEmpty()) {
                throw new MusinsaException(ErrorCode.CATEGORY_NOT_FOUND);
            } else {
                minExtremesPriceSubDto = list.stream().min(Comparator.comparingInt(ExtremesPriceSubDto::price)).orElse(null);
                maxExtremesPriceSubDto = list.stream().max(Comparator.comparingInt(ExtremesPriceSubDto::price)).orElse(null);
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return new ExtremesPriceDto(
                Category.valueOf(categoryName).label,
                minExtremesPriceSubDto,
                maxExtremesPriceSubDto
        );
    }

    @Transactional
    @CacheEvict(value = {"minPrice","minPriceBrand","extremesPrice"},  allEntries = true)
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
    @CacheEvict(value = {"minPrice","minPriceBrand","extremesPrice"},  allEntries = true)
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
    @CacheEvict(value = {"minPrice","minPriceBrand","extremesPrice"},  allEntries = true)
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
