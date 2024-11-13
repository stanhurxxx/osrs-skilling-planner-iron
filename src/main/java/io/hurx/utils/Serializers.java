package io.hurx.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import io.hurx.annotations.ManyToOne;
import io.hurx.annotations.OneToOne;
import io.hurx.models.repository.Repository;
import io.hurx.plugin.Plugin;

import java.io.IOException;
import java.lang.reflect.Field;

public class Serializers {
    /** Serializes a repository */
    public static class Repository<V extends io.hurx.models.repository.Repository<?>> extends JsonSerializer<V> {
        @SuppressWarnings("unchecked")
        @Override
        public void serialize(V value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            if (value != null) {
                // Start writing a JSON object
                gen.writeStartObject();

                Class<?> clazz = value.getClass();
                while (clazz != null && clazz != clazz.getSuperclass()) {
                    loop:
                    for (Field field : clazz.getDeclaredFields()) {
                        boolean log = false;
                        try {
                            Class<?> annotationClazz = clazz;
                            field.setAccessible(true);
                            boolean jsonIgnore = false;
                            boolean serializationIgnore = false;
                            while (annotationClazz != null && annotationClazz.getSuperclass() != annotationClazz) {
                                try {
                                    Field annotationField = annotationClazz.getDeclaredField(field.getName());
                                    if (annotationField.isAnnotationPresent(JsonIgnore.class)) {
                                        jsonIgnore = true;
                                    }
                                    if (annotationField.isAnnotationPresent(OneToOne.class)) {
                                        serializationIgnore = true;
                                    }
                                }
                                catch (Exception ignored) {}
                                annotationClazz = annotationClazz.getSuperclass();
                            }
                            if (jsonIgnore) continue;
                            if (serializationIgnore && !Deserializers.includeSerializationIgnoreFields()) continue;
                            if (!Deserializers.includeSerializationIgnoreFields() && field.isAnnotationPresent(ManyToOne.class)) {
                                log = true;
                                field.setAccessible(true);
                                gen.writeFieldName(field.getName());
                                gen.writeStartArray();
                                Object fieldValue = field.get(value);
                                if (fieldValue instanceof io.hurx.models.repository.Repository.Property.List) {
                                    for (io.hurx.models.repository.Repository.Property<io.hurx.models.repository.Repository<?>> v : ((io.hurx.models.repository.Repository.Property.List<io.hurx.models.repository.Repository<?>>) fieldValue).properties()) {
                                        gen.writeString(v.get().uuid());
                                    }
                                }
                                gen.writeEndArray();
                            }
                            else {
                                try {
                                    // Get the value of the field from the object
                                    Object fieldValue = field.get(value);

                                    // Write the field name and its value to the JSON output
                                    gen.writeFieldName(field.getName());

                                    if (fieldValue == null) {
                                        gen.writeNull();
                                    }
                                    else {
                                        Json.objectMapper.writeValue(gen, fieldValue);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        } catch (Exception ex) {
                            if (log) ex.printStackTrace();
                        }
                    }
                    clazz = clazz.getSuperclass();
                }

                // Finish writing the JSON object
                gen.writeEndObject();
            }
            else {
                gen.writeNull();
            }
        }
    }

    /** Serializes a property */
    public static class Property<V> extends JsonSerializer<io.hurx.models.repository.Repository.Property<V>> {
        @Override
        public void serialize(io.hurx.models.repository.Repository.Property<V> value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            if (value != null) {
                gen.writeObject(value.get());
            }
            else {
                gen.writeNull();
            }
        }
    }
}
