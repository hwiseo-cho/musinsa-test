package com.musinsa.pratice.mapper;

import com.musinsa.pratice.dto.request.BrandRequestDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BrandMapper {

    @Select("SELECT COUNT(*) FROM TB_BRAND WHERE ID = #{id}")
    boolean existById(@Param("id") int id);

    public void register(BrandRequestDto brandRequestDto);

    public void update(BrandRequestDto brandRequestDto);

    public void delete(BrandRequestDto brandRequestDto);

    @Select("SELECT COUNT(*) FROM TB_BRAND WHERE NAME = #{name}")
    boolean existByName(@Param("name")String name);

    @Select("SELECT COUNT(*) FROM TB_BRAND WHERE NAME = #{brandRequestDto.name} AND ID != #{brandRequestDto.id}")
    boolean existByIdAndName(@Param("brandRequestDto") BrandRequestDto brandRequestDto);
}
