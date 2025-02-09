package com.musinsa.pratice.exception.handler;

import com.musinsa.pratice.dto.response.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public CommonResponse<?> handlerRuntimeException(RuntimeException e) {
        return new CommonResponse<>(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResponse<?> handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return new CommonResponse<>(HttpStatus.BAD_REQUEST, e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }

}
