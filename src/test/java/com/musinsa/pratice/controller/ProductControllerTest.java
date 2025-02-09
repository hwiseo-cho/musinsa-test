package com.musinsa.pratice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musinsa.pratice.dto.request.ProductRequestDto;
import com.musinsa.pratice.enums.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("상품 통합테스트")
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ProductControllerTest {

    private static final String SUCCESS = "SUCCESS";
    private static final String ERROR = "BAD_REQUEST";

    @Autowired
    private MockMvc mockMvc;

    @Test
    void 구현1_카테고리별_상품_최저가격의_브랜드와_가격_총액_조회() throws Exception {

        mockMvc.perform(get("/product/min-price"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(SUCCESS))
                .andExpect(jsonPath("$.response.totalPrice").value("34,100")) // totalPrice 확인
                .andExpect(jsonPath("$.response.list.length()").value(8)) // 리스트 크기 확인
                .andExpect(jsonPath("$.response.list[0].categoryName").value("상의")) // 첫 번째 항목 검증
                .andExpect(jsonPath("$.response.list[0].brand").value("C"))
                .andExpect(jsonPath("$.response.list[0].price").value("10,000"))
                .andExpect(jsonPath("$.response.list[7].categoryName").value("악세서리")) // 마지막 항목 검증
                .andExpect(jsonPath("$.response.list[7].brand").value("F"))
                .andExpect(jsonPath("$.response.list[7].price").value("1,900"));

    }

    @Test
    void 구현2_단일브랜드로_모든품목총합_최저가_정보() throws Exception {

        mockMvc.perform(get("/product/brand/min-price"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(SUCCESS))
                .andExpect(jsonPath("$.response.최저가.브랜드").value("D")) // 최저가 브랜드 확인
                .andExpect(jsonPath("$.response.최저가.총액").value("36,100")) // 총액 확인
                .andExpect(jsonPath("$.response.최저가.카테고리.length()").value(8)) // 카테고리 개수 확인
                .andExpect(jsonPath("$.response.최저가.카테고리[0].카테고리").value("상의"))
                .andExpect(jsonPath("$.response.최저가.카테고리[0].가격").value("10,100"))
                .andExpect(jsonPath("$.response.최저가.카테고리[1].카테고리").value("아우터"))
                .andExpect(jsonPath("$.response.최저가.카테고리[1].가격").value("5,100"))
                .andExpect(jsonPath("$.response.최저가.카테고리[7].카테고리").value("악세서리"))
                .andExpect(jsonPath("$.response.최저가.카테고리[7].가격").value("2,000"));

    }

    @Test
    void 구현3_카테고리별_상품_최저가격의_브랜드와_가격_총액_조회() throws Exception {

        mockMvc.perform(get("/product/TOP/extremes-price"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(SUCCESS))
                .andExpect(jsonPath("$.response.카테고리").value("상의")) // 카테고리 확인
                .andExpect(jsonPath("$.response.최저가.브랜드").value("C"))
                .andExpect(jsonPath("$.response.최저가.가격").value("10,000"))
                .andExpect(jsonPath("$.response.최고가.브랜드").value("I"))
                .andExpect(jsonPath("$.response.최고가.가격").value("11,400"));

    }

    @Test
    void 상품추가_성공() throws Exception {

        ProductRequestDto productRequestDto = new ProductRequestDto(1, 1, 30000);

        mockMvc.perform(post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(productRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(SUCCESS));

    }

    @Test
    void 상품추가_실패() throws Exception {

        // 1. 존재하지 않는 카테고리일 때
        ProductRequestDto productRequestDto1 = new ProductRequestDto(111, 1, 30000);

        mockMvc.perform(post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(productRequestDto1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(ERROR))
                .andExpect(jsonPath("$.message").value(ErrorCode.CATEGORY_NOT_FOUND.getMessage()));

        // 2. 존재하지 않는 브랜드일 때
        ProductRequestDto productRequestDto2 = new ProductRequestDto(1, 111, 30000);

        mockMvc.perform(post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(productRequestDto2)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(ERROR))
                .andExpect(jsonPath("$.message").value(ErrorCode.BRAND_NOT_FOUND.getMessage()));

        // 3. 빈 값이 왔을 때
        mockMvc.perform(post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString("")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(ERROR));

    }

    @Test
    void 상품업데이트_성공() throws Exception {

        ProductRequestDto productRequestDto = new ProductRequestDto(1,1, 1, 30000);

        mockMvc.perform(patch("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(productRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(SUCCESS));

    }

    @Test
    void 상품업데이트_실패() throws Exception {

        // 1. 존재하지 않는 상품일 때
        ProductRequestDto productRequestDto = new ProductRequestDto(0,1, 1, 30000);

        mockMvc.perform(patch("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(productRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(ERROR))
                .andExpect(jsonPath("$.message").value(ErrorCode.PRODUCT_NOT_FOUND.getMessage()));

        // 2. 빈 값이 왔을 때
        mockMvc.perform(patch("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString("")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(ERROR));

    }

    @Test
    void 상품삭제_성공() throws Exception {

        ProductRequestDto productRequestDto = new ProductRequestDto(1);

        mockMvc.perform(delete("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(productRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(SUCCESS));

    }
    @Test
    void 상품삭제_실패() throws Exception {

        // 1. 존재하지 않는 상품일 때
        ProductRequestDto productRequestDto = new ProductRequestDto(1111);

        mockMvc.perform(delete("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(productRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(ERROR))
                .andExpect(jsonPath("$.message").value(ErrorCode.PRODUCT_NOT_FOUND.getMessage()));


        // 2. 빈 값이 왔을 때
        mockMvc.perform(delete("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString("")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(ERROR));

    }

}

