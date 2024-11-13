package io.hurx.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import io.hurx.annotations.ManyToOne;
import io.hurx.annotations.OneToOne;
import io.hurx.models.repository.Repository;
import io.hurx.plugin.Plugin;
import io.hurx.repository.slayer.SlayerRepository;

import java.io.IOException;
import java.lang.reflect.*;
import java.util.Iterator;
import java.util.UUID;

import static io.hurx.utils.Deserializers.Repository.createRepositoryFromFileName;

public class Deserializers {
    /** SET Ignores @JsonIgnore annotations while (de)serializing */
    public static void includeSerializationIgnoreFields(boolean includeSerializationIgnoreFields) {
        Deserializers.includeSerializationIgnoreFields = includeSerializationIgnoreFields;
    }
    /** GET Ignores @JsonIgnore annotations while (de)serializing */
    public static boolean includeSerializationIgnoreFields() {
        return includeSerializationIgnoreFields;
    }
    /** Ignores @JsonIgnore annotations while (de)serializing */
    private static boolean includeSerializationIgnoreFields = false;

    /** GET Whether the upload just started */
    public static boolean justStartedUpload() {
        return justStartedUpload;
    }
    /** SET Whether the upload just started */
    public static void justStartedUpload(boolean value) {
        justStartedUpload = value;
    }
    /** Whether the upload just started */
    private static boolean justStartedUpload = false;

    public static class Repository <V> extends JsonDeserializer<V> {
        /** GET The current repository class */
        public static Class<?> currentClass() {
            return currentClass;
        }
        /** SET The current repository class */
        public static void currentClass(Class<?> clazz) {
            currentClass = clazz;
        }
        /** The current repository class */
        public static Class<?> currentClass;

        /** SET The last field that was being (de)serialized */
        public static void currentField(Field currentField) {
            Repository.currentField = currentField;
        }
        /** GET The last field that was being (de)serialized */
        public static Field currentField() {
            return currentField;
        }
        /** The last field that was being (de)serialized */
        private static Field currentField;

        /** SET The current repository node */
        public static void currentNode(JsonNode currentNode) {
            Repository.currentNode = currentNode;
        }
        /** GET The current repository node */
        public static JsonNode currentNode() {
            return currentNode;
        };
        /** The current repository node */
        private static JsonNode currentNode;

        /** Creates a repository from a file name and class */
        public static io.hurx.models.repository.Repository<?> createRepositoryFromFileName(Class<?> clazz, String fileName) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InvocationTargetException {
            io.hurx.models.repository.Repository<?> instance = null;
            Constructor<?> constructor = null;
            if (fileName != null) {
                for (Constructor<?> c : clazz.getDeclaredConstructors()) {
                    if (c.getParameters().length == 2 && io.hurx.models.repository.Repository.class.isAssignableFrom(c.getParameters()[0].getType()) && String.class.isAssignableFrom(c.getParameters()[1].getType())) {
                        // priority
                        constructor = c;
                        break;
                    }
                    else if (c.getParameters().length == 2 && Plugin.class.isAssignableFrom(c.getParameters()[0].getType()) && String.class.isAssignableFrom(c.getParameters()[1].getType())) {
                        constructor = c;
                        fileName = Plugin.accountHash();
                    }
                }
            }
            if (constructor == null) {
                for (Constructor<?> c : clazz.getDeclaredConstructors()) {
                    if (c.getParameters().length == 1 && io.hurx.models.repository.Repository.class.isAssignableFrom(c.getParameters()[0].getType())) {
                        constructor = c;
                        break;
                    }
                    else if (c.getParameters().length == 1 && Plugin.class.isAssignableFrom(c.getParameters()[0].getType())) {
                        constructor = c;
                        break;
                    }
                }
                if (constructor == null) {
                    return null;
                }
                else {
                    instance = (io.hurx.models.repository.Repository<?>)constructor.newInstance(Injects.getInjectableValues().get(constructor.getParameters()[0].getType()));
                }
            }
            else {
                instance = (io.hurx.models.repository.Repository<?>)constructor.newInstance(Injects.getInjectableValues().get(constructor.getParameters()[0].getType()), fileName);
            }
            // Load from internal cache
            instance = io.hurx.models.repository.Repository.registered.get(instance.generatePath());
            return instance;
        }

        @SuppressWarnings("unchecked")
        @Override
        public V deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            JsonNode node = currentNode();
            V instance = null;
            try {
                instance = includeSerializationIgnoreFields && justStartedUpload
                    ? (V) createRepositoryFromFileName(currentClass, UUID.randomUUID().toString())
                    : (V) createRepositoryFromFileName(currentClass, node.has("uuid") ? node.get("uuid").asText() : null);
            } catch (Exception ex) {
                System.out.println("Could not instantiate instance in repository deserializer: " + currentClass().getName());
                throw new RuntimeException(ex);
            }

            justStartedUpload(false);

            if (instance == null) return (V) instance;

            // Temporarily change the injectables, store it in a variable
            Object classInstance = Injects.getInjectableValues().get(currentClass);
            Injects.setInjectable((Class<V>) currentClass, instance);

            // Currents
            Class<?> currentClass = currentClass();
            Field currentField = currentField();

            // Deserialization
            if (node.isObject()) {
                Iterator<java.util.Map.Entry<String, JsonNode>> fields = node.fields();

                loop:
                while (fields.hasNext()) {
                    java.util.Map.Entry<String, JsonNode> entry = fields.next();
                    String name = entry.getKey();
                    JsonNode value = entry.getValue();
                    Class<?> nodeClazz = currentClass;
                    while (nodeClazz != null && nodeClazz != nodeClazz.getSuperclass()) {
                        // The declared field on the class
                        Field field = null;
                        try {
                            field = nodeClazz.getDeclaredField(name);
                            currentField(field);
                            currentField = field;
                            field.setAccessible(true);
                        }
                        catch (Exception ignored) {
                            nodeClazz = nodeClazz.getSuperclass();
                        }

                        // Field must exist
                        if (field == null) continue loop;

                        // Skip uuid
                        if (field.getName().equals("uuid")) continue loop;

                        // Find annotations
                        boolean jsonIgnore = false;
                        boolean oneToOne = false;
                        boolean manyToOne = false;
                        Class<?> annotationClazz = nodeClazz;
                        while (annotationClazz != null && annotationClazz.getSuperclass() != annotationClazz) {
                            try {
                                Field annotationField = annotationClazz.getDeclaredField(field.getName());
                                if (annotationField.isAnnotationPresent(JsonIgnore.class)) {
                                    jsonIgnore = true;
                                }
                                if (annotationField.isAnnotationPresent(OneToOne.class)) {
                                    oneToOne = true;
                                }
                                if (annotationField.isAnnotationPresent(ManyToOne.class)) {
                                    manyToOne = true;
                                }
                                break;
                            } catch (Exception ignored) {}
                            annotationClazz = annotationClazz.getSuperclass();
                        }

                        // Ignored
                        if (jsonIgnore) continue loop;

                        // One to one relations
                        if (oneToOne && !includeSerializationIgnoreFields) {
                            try {
                                field.set(instance, createRepositoryFromFileName(field.getType(), null).initialize());
                            } catch (Exception ex) {
                                System.out.println("Couldn't instantiate one-to-one relation");
                                ex.printStackTrace();
                            }
                            continue loop;
                        }

                        // Null values
                        if (value.isNull()) {
                            if (io.hurx.models.repository.Repository.Property.Map.class.isAssignableFrom(field.getType())) {
                                io.hurx.models.repository.Repository.Property.Map<?, ?> property = null;
                                try {
                                    property = (io.hurx.models.repository.Repository.Property.Map<?, ?>) field.get(instance);
                                } catch (IllegalAccessException e) {
                                    System.out.println("Field is not accessible, code has been altered.");
                                    e.printStackTrace();
                                }
                                property.clear();
                            } else if (io.hurx.models.repository.Repository.Property.List.class.isAssignableFrom(field.getType())) {
                                io.hurx.models.repository.Repository.Property.List<?> property = null;
                                try {
                                    property = (io.hurx.models.repository.Repository.Property.List<?>) field.get(instance);
                                } catch (IllegalAccessException e) {
                                    System.out.println("Field is not accessible, code has been altered.");
                                    e.printStackTrace();
                                }
                                property.clear();
                            } else if (io.hurx.models.repository.Repository.Property.class.isAssignableFrom(field.getType())) {
                                io.hurx.models.repository.Repository.Property<?> property = null;
                                try {
                                    property = (io.hurx.models.repository.Repository.Property<?>) field.get(instance);
                                } catch (IllegalAccessException e) {
                                    System.out.println("Field is not accessible, code has been altered.");
                                    e.printStackTrace();
                                }
                                property.replace(null);
                            } else {
                                try {
                                    field.set(instance, null);
                                } catch (IllegalAccessException e) {
                                    System.out.println("Field is not accessible, code has been altered.");
                                    e.printStackTrace();
                                }
                            }
                            continue loop;
                        }
                        else {
                            if (includeSerializationIgnoreFields() && (oneToOne || manyToOne)){
                                if (oneToOne) {
                                    String json = Json.objectMapper.writeValueAsString(Json.objectMapper.readTree(value.traverse(Json.objectMapper)));
                                    Deserializers.Repository.currentClass(field.getType());
                                    Deserializers.Repository.currentNode(Json.objectMapper.readTree(json));
                                    io.hurx.models.repository.Repository<?> repo = (io.hurx.models.repository.Repository<?>) Json.objectMapper.readValue(json, field.getType());
                                    repo.isInitialized(true);
                                    try {
                                        field.set(instance, repo);
                                    } catch (Exception e) {
                                        System.out.println("One to one relation could not be deserialized");
                                        e.printStackTrace();
                                    }
                                }
                                else {
                                    try {
                                        io.hurx.models.repository.Repository.Property.List<Object> list = (io.hurx.models.repository.Repository.Property.List<Object>) field.get(instance);
                                        Type gType = field.getGenericType();
                                        if (gType instanceof ParameterizedType) {
                                            ParameterizedType param = (ParameterizedType) gType;
                                            Type type = param.getActualTypeArguments()[0];
                                            if (type instanceof Class) {
                                                for (JsonNode item : value) {
                                                    Deserializers.Repository.currentClass((Class<?>)type);
                                                    Deserializers.Repository.currentNode(item);
                                                    io.hurx.models.repository.Repository<?> repo = (io.hurx.models.repository.Repository<?>) Json.objectMapper.readValue(node.traverse(Json.objectMapper), (Class<?>)type);
                                                    repo.isInitialized(true);
                                                    list.add(repo);
                                                }
                                            }
                                        }
                                    }
                                    catch (Exception e) {
                                        System.out.println("Many to one relation could not be deserialized");
                                        e.printStackTrace();
                                    }
                                }
                                continue loop;
                            }
                            else {
                                try {
                                    String json = Json.objectMapper.writeValueAsString(Json.objectMapper.readTree(value.traverse(Json.objectMapper)));
                                    field.set(instance, Json.objectMapper.readValue(json, field.getType()));
                                } catch (Exception e) {
                                    System.out.println("Field is not accessible, code has been altered.");
                                    e.printStackTrace();
                                }
                                continue loop;
                            }
                        }
                    }
                }
            }

            // Reset injectables
            if (includeSerializationIgnoreFields() && classInstance != null) {
                Injects.setInjectable((Class<V>) currentClass, (V) classInstance);
            }

            return (V) instance;
        }
    }

    /** Property deserializer */
    public static class Property<V> extends JsonDeserializer<io.hurx.models.repository.Repository.Property<V>> {
        @SuppressWarnings({ "rawtypes", "unchecked" })
        @Override
        public io.hurx.models.repository.Repository.Property<V> deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            // Read the JSON as a JsonNode
            JsonNode node = parser.getCodec().readTree(parser);
            Field field = Repository.currentField();
            if (field == null) {
                throw new IOException("Current field is empty, so there's a Repository.Property being deserialized, before a Repository is being deserialized. They can only be used within repositories.");
            }
            if (field.getType() == io.hurx.models.repository.Repository.Property.class) {
                try {
                    Type gType = field.getGenericType();
                    if (gType instanceof ParameterizedType) {
                        ParameterizedType param = (ParameterizedType) gType;
                        Type type = param.getActualTypeArguments()[0];
                        if (type instanceof Class) {
                            String json = Json.objectMapper.writeValueAsString(Json.objectMapper.readTree(node.traverse(Json.objectMapper)));
                            return new io.hurx.models.repository.Repository.Property(Json.objectMapper.readValue(json, (Class<?>)type));
                        }
                    }
                }
                catch (Exception ex) {
                    System.out.println("Error deserializing property.");
                    ex.printStackTrace();
                }
            }
            return new io.hurx.models.repository.Repository.Property<>(null);
        }
    }

    /** Deserializer for lists */
    public static class List<V> extends JsonDeserializer<io.hurx.models.repository.Repository.Property.List<V>> {
        @SuppressWarnings("unchecked")
        @Override
        public io.hurx.models.repository.Repository.Property.List<V> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
            Field field = Repository.currentField();
            JsonNode node = p.getCodec().readTree(p);
            io.hurx.models.repository.Repository.Property.List<Object> propertyList = new io.hurx.models.repository.Repository.Property.List<>();
            if (field.getType() == io.hurx.models.repository.Repository.Property.List.class) {
                propertyList.clear();
                Type gType = field.getGenericType();
                if (gType instanceof ParameterizedType) {
                    ParameterizedType param = (ParameterizedType) gType;
                    Type type = param.getActualTypeArguments()[0];
                    if (type instanceof Class) {
                        ManyToOne manyToOne = field.isAnnotationPresent(ManyToOne.class)
                                ? field.getAnnotation(ManyToOne.class)
                                : null;
                        for (JsonNode element : node) {
                            if (manyToOne != null) {
                                try {
                                    if (element.isObject()) {
                                        propertyList.add(createRepositoryFromFileName(manyToOne.type(), element.get("uuid").asText()).initialize());
                                    }
                                    else if (element.isTextual()) {
                                        propertyList.add(createRepositoryFromFileName(manyToOne.type(), element.asText()).initialize());
                                    }
                                } catch (Exception e) {
                                    System.out.println("Couldn't instantiate repository in list from many to one relation, constructor corrupted.");
                                    e.printStackTrace();
                                }
                            }
                            else {
                                try {
                                    String json = Json.objectMapper.writeValueAsString(Json.objectMapper.readTree(element.traverse(Json.objectMapper)));
                                    propertyList.add(Json.objectMapper.readValue(json, (Class<?>)type));
                                }
                                catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
            return (io.hurx.models.repository.Repository.Property.List<V>) propertyList;
        }
    }

    /** Deserializer for maps */
    public static class Map<K, V> extends JsonDeserializer<io.hurx.models.repository.Repository.Property.Map<K, V>> {
        @SuppressWarnings("unchecked")
        @Override
        public io.hurx.models.repository.Repository.Property.Map<K, V> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
            JsonNode node = p.getCodec().readTree(p);
            Field field = Repository.currentField();
            io.hurx.models.repository.Repository.Property.Map<Object, Object> map = new io.hurx.models.repository.Repository.Property.Map<>();
            if (field == null) return null;
            if (field.getType() == io.hurx.models.repository.Repository.Property.Map.class) {
                Type gType = field.getGenericType();
                if (gType instanceof ParameterizedType) {
                    ParameterizedType param = (ParameterizedType) gType;
                    Type keyType = param.getActualTypeArguments()[0];
                    Type valueType = param.getActualTypeArguments()[1];
                    if (keyType instanceof Class && valueType instanceof Class) {
                        if (node.isObject()) {
                            Iterator<java.util.Map.Entry<String, JsonNode>> iterator = node.fields();
                            while (iterator.hasNext()) {
                                java.util.Map.Entry<String, JsonNode> entry = iterator.next();
                                String jsonKey = Json.objectMapper.writeValueAsString(entry.getKey());
                                String jsonValue = Json.objectMapper.writeValueAsString(Json.objectMapper.readTree(entry.getValue().traverse(Json.objectMapper)));
                                map.set(
                                    Json.objectMapper.readValue(jsonKey, (Class<?>) keyType),
                                    Json.objectMapper.readValue(jsonValue, (Class<?>) valueType)
                                );
                            }
                        }
                    }
                }
            }
            return (io.hurx.models.repository.Repository.Property.Map<K, V>)map;
        }
    }
}
