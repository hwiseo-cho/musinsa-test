package com.musinsa.pratice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musinsa.pratice.dto.request.BrandRequestDto;
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

@DisplayName("브랜드 통합테스트")
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class BrandControllerTest {

    private static final String SUCCESS = "SUCCESS";
    private static final String ERROR = "BAD_REQUEST";

    @Autowired
    private MockMvc mockMvc;

    @Test
    void 브랜드추가_성공() throws Exception {

        BrandRequestDto brandRequestDto = new BrandRequestDto(-1, "J");

        mockMvc.perform(post("/brand/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(brandRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(SUCCESS));

    }

    @Test
    void 브랜드추가_실패() throws Exception {

        // 1. 이미 같은 이름의 브랜드가 있을 때
        BrandRequestDto brandRequestDto1 = new BrandRequestDto(-1, "A");

        mockMvc.perform(post("/brand/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(brandRequestDto1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(ERROR))
                .andExpect(jsonPath("$.message").value(ErrorCode.BRAND_NAME_DUPLICATE.getMessage()));


        // 2. 빈 값으로 왔을 때
        mockMvc.perform(post("/brand/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString("")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(ERROR));
    }

    @Test
    void 브랜드수정_성공() throws Exception {

        BrandRequestDto brandRequestDto = new BrandRequestDto(1, "K");

        mockMvc.perform(patch("/brand/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(brandRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(SUCCESS));

    }

    @Test
    void 브랜드수정_실패() throws Exception {

        // 1. 존재 하지 않는 브랜드일 때
        BrandRequestDto brandRequestDto1 = new BrandRequestDto(0, "K");

        mockMvc.perform(patch("/brand/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(brandRequestDto1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(ERROR))
                .andExpect(jsonPath("$.message").value(ErrorCode.BRAND_NOT_FOUND.getMessage()));


        // 2. 중복되는 이름이 존재할 때
        BrandRequestDto brandRequestDto2 = new BrandRequestDto(1, "C");

        mockMvc.perform(patch("/brand/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(brandRequestDto2)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(ERROR))
                .andExpect(jsonPath("$.message").value(ErrorCode.BRAND_NAME_DUPLICATE.getMessage()));


        // 3. 빈 값이 왔을 때
        mockMvc.perform(patch("/brand/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString("")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(ERROR));
    }

    @Test
    void 브랜드삭제_성공() throws Exception {

        // 삭제할 신규 브랜드 추가
        BrandRequestDto brandRequestDto1 = new BrandRequestDto(0, "J");
        mockMvc.perform(post("/brand/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(brandRequestDto1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(SUCCESS));

        BrandRequestDto brandRequestDto2 = new BrandRequestDto(10);

        mockMvc.perform(delete("/brand/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(brandRequestDto2)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(SUCCESS));

    }

    @Test
    void 브랜드삭제_실패() throws Exception {

        // 1. 존재하지 않는 브랜드일 때
        BrandRequestDto brandRequestDto1 = new BrandRequestDto(0);

        mockMvc.perform(delete("/brand/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(brandRequestDto1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(ERROR))
                .andExpect(jsonPath("$.message").value(ErrorCode.BRAND_NOT_FOUND.getMessage()));

        // 2. 삭제할 브랜드의 상품이 아직 존재할 때
        BrandRequestDto brandRequestDto = new BrandRequestDto(1);

        mockMvc.perform(delete("/brand/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(brandRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(ERROR))
                .andExpect(jsonPath("$.message").value(ErrorCode.PRODUCT_IS_EXIST.getMessage()));

    }

}