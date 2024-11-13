package io.hurx.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.gson.JsonObject;
import io.hurx.annotations.ManyToOne;
import io.hurx.annotations.SerializationIgnore;
import io.hurx.models.repository.Repository;
import io.hurx.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.*;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class Deserializer {

    /** The full node */
    private final JsonNode node;

    /** Repository class */
    private final Class<?> clazz;

    /** Current field */
    private Field field;

    /** The repository instance */
    private Repository<?> instance;

    public Deserializer(File file, Class<?> clazz) throws IOException {
        node = Json.objectMapper.readTree(file);
        this.clazz = clazz;
    }

    public Deserializer(Repository<?> repository, Class<?> clazz) throws IOException {
        node = Json.objectMapper.readTree(new Serializer(repository).serialize());
        this.clazz = clazz;
    }

    public Deserializer(String json, Class<?> clazz) throws IOException {
        node = Json.objectMapper.readTree(json);
        this.clazz = clazz;
    }

    public Deserializer(JsonNode node, Class<?> clazz) {
        this.node = node;
        this.clazz = clazz;
    }

    /** Deserializes a repository */
    @SuppressWarnings("CallToPrintStackTrace")
    public Repository<?> deserialize() throws IOException, InvocationTargetException, InstantiationException, IllegalAccessException {
        if (!node.isObject()) {
            throw new IOException("Node is not a repository or object.");
        }
        instance = createRepositoryFromFileName(clazz, node.has("uuid") ? node.get("uuid").asText() : null);
        if (instance == null) {
            throw new IOException("Node could not be instantiated to a repository.");
        }
        Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
        while (fields.hasNext()) {
            try {
                Map.Entry<String, JsonNode> entry = fields.next();
                Class<?> c = clazz;
                while (c != null && c != c.getSuperclass()) {
                    try {
                        field = c.getField(entry.getKey());
                        break;
                    }
                    catch (Exception ignored) {}
                    c = c.getSuperclass();
                }
                if (field == null) continue;
                field.setAccessible(true);
                JsonIgnore jsonIgnore = field.isAnnotationPresent(JsonIgnore.class)
                    ? field.getAnnotation(JsonIgnore.class)
                    : null;
                SerializationIgnore serializationIgnore = field.isAnnotationPresent(SerializationIgnore.class)
                    ? field.getAnnotation(SerializationIgnore.class)
                    : null;
                ManyToOne manyToOne = field.isAnnotationPresent(ManyToOne.class)
                    ? field.getAnnotation(ManyToOne.class)
                    : null;
                if (jsonIgnore != null) continue;
                if (serializationIgnore != null && !includeSerializationIgnoreFields) continue;
                if (manyToOne != null && entry.getValue().isArray()) {
                    deserializeManyToOne(entry, manyToOne);
                }
                else {
                    performDeserialization(field, entry);
                }
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return instance;
    }

    /** Performs the deserialization */
    private void performDeserialization(Field field, Map.Entry<String, JsonNode> entry) throws IOException, IllegalAccessException, InvocationTargetException, InstantiationException {
        if (field.getType() == Repository.Property.List.class) {
            deserializeList(entry);
        }
        else if (Repository.Property.Map.class == field.getType()) {
            deserializeMap(entry);
        }
        else if (Repository.Property.class == field.getType()) {
            deserializeProperty(entry);
        }
        else if (Repository.class == field.getType()) {
            field.set(instance, deserializeRepository(entry));
        }
        else {
            field.set(instance, entry.getValue().traverse(Json.objectMapper).readValueAs(field.getType()));
        }
    }

    /** Deserializes a many to one list */
    @SuppressWarnings("unchecked")
    private void deserializeManyToOne(Map.Entry<String, JsonNode> entry, ManyToOne manyToOne) throws IOException, IllegalAccessException, InvocationTargetException, InstantiationException {
        if (!entry.getValue().isArray()) {
            throw new IOException("Many to one of field " + field.getName() + " was not an array.");
        }
        Repository.Property.List<Object> list = (Repository.Property.List<Object>) field.get(instance);
        for (JsonNode node : entry.getValue()) {
            if (node.isObject()) {
                if (!node.has("uuid")) continue;
                Repository<?> repository = createRepositoryFromFileName(manyToOne.type(), node.get("uuid").asText());
                if (repository == null) continue;
                list.add(repository);
            }
            else if (node.isTextual()) {
                Repository<?> repository = createRepositoryFromFileName(manyToOne.type(), node.asText());
                if (repository == null) continue;
                list.add(repository);
            }
        }
    }

    /** Deserializes a list */
    @SuppressWarnings("unchecked")
    private void deserializeList(Map.Entry<String, JsonNode> entry) throws IOException, IllegalAccessException {
        if (!entry.getValue().isArray()) {
            throw new IOException("List of field " + field.getName() + " is not an array.");
        }
        Repository.Property.List<Object> propertyList = (Repository.Property.List<Object>) field.get(instance);
        propertyList.clear();
        Type gType = field.getGenericType();
        if (gType instanceof ParameterizedType) {
            ParameterizedType param = (ParameterizedType) gType;
            Type type = param.getActualTypeArguments()[0];
            for (JsonNode node : entry.getValue()) {
                propertyList.add(
                    node.traverse(Json.objectMapper).readValuesAs((Class<?>)type)
                );
            }
        }
    }

    /** Deserializes a map */
    @SuppressWarnings({"unchecked", "rawtypes"})
    private void deserializeMap(Map.Entry<String, JsonNode> entry) throws IOException, IllegalAccessException {
        if (!entry.getValue().isObject()) {
            throw new IOException("List of field " + field.getName() + " is not an array.");
        }
        Repository.Property.Map<Object, Object> propertyMap = (Repository.Property.Map<Object, Object>) field.get(instance);
        LinkedHashMap linkedHashMap = Json.objectMapper.readValue(entry.getValue().traverse(Json.objectMapper), LinkedHashMap.class);
        propertyMap.clear();
        Type gType = field.getGenericType();
        if (gType instanceof ParameterizedType) {
            ParameterizedType param = (ParameterizedType) gType;
            Type typeA = param.getActualTypeArguments()[0];
            Type typeB = param.getActualTypeArguments()[0];
            if (entry.getValue().isObject()) {
                Iterator<Map.Entry<String, JsonNode>> iterator = entry.getValue().fields();
                while (iterator.hasNext()) {
                    Map.Entry<String, JsonNode> next = iterator.next();
                    propertyMap.set(
                        Json.objectMapper.readValue(next.getKey(), (Class<?>) typeA),
                        next.getValue().traverse(Json.objectMapper).readValuesAs((Class<?>) typeB)
                    );
                }
            }
        }
    }

    /** Deserializes a property */
    @SuppressWarnings("unchecked")
    private void deserializeProperty(Map.Entry<String, JsonNode> entry) throws IllegalAccessException, IOException {
        Type gType = field.getGenericType();
        if (gType instanceof ParameterizedType) {
            ParameterizedType param = (ParameterizedType) gType;
            Type type = param.getActualTypeArguments()[0];
            Repository.Property<Object> property = (Repository.Property<Object>) field.get(instance);
            property.replace(entry.getValue().traverse(Json.objectMapper).readValuesAs((Class<?>)type));
        }
    }

    /** Deserializes a repository */
    private Repository<?> deserializeRepository(Map.Entry<String, JsonNode> entry) throws IOException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return new Deserializer(entry.getValue(), field.getType()).deserialize();
    }

    /**
     * Static function to create a repository based on a class and a filename. If it throws an exception, something is wrong with the code.
     * @param clazz the class
     * @param fileName the filename
     * @return the repository, or null if failed.
     */

}
