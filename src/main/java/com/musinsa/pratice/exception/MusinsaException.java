package com.musinsa.pratice.exception;

import com.musinsa.pratice.enums.ErrorCode;

public class MusinsaException extends RuntimeException{
    public MusinsaException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }
}
