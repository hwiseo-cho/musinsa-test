package com.musinsa.pratice.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CategoryMapper {

    @Select("SELECT COUNT(*) FROM TB_CATEGORY WHERE ID = #{id}")
    boolean existById(@Param("id") int id);

}
