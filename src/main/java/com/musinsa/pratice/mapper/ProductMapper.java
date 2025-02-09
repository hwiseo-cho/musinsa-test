package com.musinsa.pratice.mapper;

import com.musinsa.pratice.dto.request.ProductRequestDto;
import com.musinsa.pratice.dto.response.ExtremesPriceSubDto;
import com.musinsa.pratice.dto.response.ProductDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.Mapping;

import java.util.List;

@Mapper
public interface ProductMapper {

    public List<ProductDto> findAll();

    public List<ExtremesPriceSubDto> extremesPrice(String categoryName);

    public void register(ProductRequestDto productRequestDto);

    public void update(ProductRequestDto productRequestDto);

    @Select("SELECT COUNT(*) FROM TB_PRODUCT WHERE ID = #{id}")
    boolean existById(int id);

    public void delete(ProductRequestDto productRequestDto);

    @Select("SELECT COUNT(*) FROM TB_PRODUCT WHERE BRAND_ID = #{id}")
    boolean existByBrandId(int id);
}
