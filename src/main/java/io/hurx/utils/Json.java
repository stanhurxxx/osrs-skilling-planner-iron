package io.hurx.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.Field;

/** Json parsing utility */
public class Json {
    /** The default object mapper to use when (de)serializing */
    public static final ObjectMapper objectMapper = new ObjectMapper();

    /** SET The last field that was being (de)serialized */
    public static void currentField(Field currentField) {
        Json.currentField = currentField;
    }
    /** GET The last field that was being (de)serialized */
    public static Field currentField() {
        return currentField;
    }
    /** The last field that was being (de)serialized */
    private static Field currentField;

    /** SET Ignores @JsonIgnore annotations while (de)serializing */
    public static void includeSerializationIgnoreFields(boolean includeSerializationIgnoreFields) {
        Json.includeSerializationIgnoreFields = includeSerializationIgnoreFields;
    }
    /** GET Ignores @JsonIgnore annotations while (de)serializing */
    public static boolean includeSerializationIgnoreFields() {
        return includeSerializationIgnoreFields;
    }
    /** Ignores @JsonIgnore annotations while (de)serializing */
    private static boolean includeSerializationIgnoreFields = false;
}
