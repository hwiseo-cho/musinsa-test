package com.musinsa.pratice.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.DecimalFormat;

public class IntToMoneySerializer extends JsonSerializer<Integer> {
    private static final DecimalFormat decimalFormat = new DecimalFormat("#,###");

    /**
     * 숫자를 금액 형태로 포맷팅 ex) 1000 -> 1,000
     */
    @Override
    public void serialize(Integer value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value != null) {
            gen.writeString(decimalFormat.format(value));
        } else {
            gen.writeNull();
        }
    }
}