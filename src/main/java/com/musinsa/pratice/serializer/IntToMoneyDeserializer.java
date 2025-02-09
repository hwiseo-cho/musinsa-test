package com.musinsa.pratice.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;

public class IntToMoneyDeserializer extends JsonDeserializer<Integer> {
    @Override
    public Integer deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String priceStr = jsonParser.getText();
        try {
            return Integer.parseInt(priceStr.replaceAll(",", "")); // ✅ 콤마 제거 후 int 변환
        } catch (NumberFormatException e) {
            throw new IOException(e.getMessage());
        }
    }
}
