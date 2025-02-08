package com.musinsa.pratice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommonResponse<T> {

    private HttpStatus status;
    private String message;
    private T response;

    public CommonResponse(HttpStatus httpStatus, String message) {
        this(httpStatus, message, null);
    }

}
