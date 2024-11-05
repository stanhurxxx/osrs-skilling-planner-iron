package io.hurx.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.Field;

public class Json {
    public static final ObjectMapper objectMapper = new ObjectMapper();

    private static Field currentField;

    public static Field getCurrentField() {
        return currentField;
    }

    public static void setCurrentField(Field currentField) {
        Json.currentField = currentField;
    }
}
