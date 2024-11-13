package io.hurx.models.repository;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.*;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

import io.hurx.annotations.OneToOne;
import io.hurx.models.repository.exceptions.RepositoryFileCorruptedException;
import io.hurx.annotations.ManyToOne;
import io.hurx.models.IconPaths;
import io.hurx.models.repository.exceptions.PlayerNotLoggedInException;
import io.hurx.plugin.Plugin;
import io.hurx.repository.PluginRepository;
import io.hurx.utils.Deserializers;
import io.hurx.utils.Injects;
import io.hurx.utils.Json;
import io.hurx.utils.Serializers;

/**
 * Abstract class representing a repository for managing cached data.
 *
 * @param <R> The type of the repository that extends this class.
 */
@JsonDeserialize(using = Deserializers.Repository.class)
@JsonSerialize(using = Serializers.Repository.class)
public abstract class Repository<R extends Repository<?>> {
    /** Registered repositories */
    @JsonIgnore
    public static Map<String, Repository<?>> registered = new HashMap<>();

    /** Checks all Repository.Property properties to see if they are marked as dirty. */
    public boolean isDirty() {
        for (Field field : getClass().getDeclaredFields()) {
            if (Repository.Property.class.isAssignableFrom(field.getType())) {
                try {
                    Property<?> property = (Property<?>) field.get(this);
                    if (property.isDirty()) {
                        return true;
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return false;
    }
    /** Marks all Repository.Property properties to be not dirty anymore. */
    public void isDirty(boolean isDirty) {
        for (Field field : getClass().getDeclaredFields()) {
            if (Repository.Property.class.isAssignableFrom(field.getType())) {
                try {
                    Property<?> property = (Property<?>) field.get(this);
                    if (property == null) continue;
                    property.isDirty(isDirty);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /** GET: Was the repository dirty before the last render? */
    @JsonIgnore
    public boolean wasDirty() {
        if (this.isDirty()) {
            this.wasDirty = true;
        }
        return this.wasDirty;
    }
    /** SET: Was the repository dirty before the last render? */
    @JsonIgnore
    public void wasDirty(boolean wasDirty) {
        for (Field field : getClass().getDeclaredFields()) {
            if (Repository.Property.class.isAssignableFrom(field.getType())) {
                try {
                    Property<?> property = (Property<?>) field.get(this);
                    if (property == null) continue;
                    if (property.isDirty() && wasDirty) {
                        property.wasDirty(true);
                    }
                    else if (!wasDirty) {
                        property.wasDirty(false);
                    }
                    property.wasDirty(wasDirty);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        this.wasDirty = wasDirty;
    }
    /** Was the repository dirty before the last render? */
    @JsonIgnore
    private boolean wasDirty = true;

    @JsonIgnore
    public <T extends Repository<?>> T get(Class<T> clazz, String fileName) {
        return null;
    }

    /**
     * The directory where the cache is stored.
     */
    @JsonIgnore
    public final static File CACHE_DIR = new File(Plugin.HOME_DIR.getAbsolutePath());

    /** GET Last modification timestamp */
    public long lastModified() {
        return lastModified;
    }
    /** SET Last modification timestamp */
    public void lastModified(long lastModified) {
        this.lastModified = lastModified;
    }
    /** Last modification timestamp */
    protected long lastModified = 0;

    /** GET Whether or not the repository has been initialized */
    public boolean isInitialized() {
        return isInitialized;
    }
    /** SET Whether or not the repository has been initialized */
    public void isInitialized(boolean isInitialized) {
        this.isInitialized = isInitialized;
    }
    /** Whether or not the repository has been initialized */
    protected boolean isInitialized = false;

    /** Reference to the parent repository */
    @JsonIgnore
    private R parent;

    /** The name of the file associated with the repository */
    protected String uuid;

    /** Reference to the associated Plugin instance */
    @JsonIgnore
    protected Plugin plugin;

    /**
     * Whether the repository is validated, for in combo boxes.
     */
    public boolean isValidated() {
        return true;
    }

    /**
     * Gets the icon path for the repository for in combo boxes.
     */
    public IconPaths getIconPath() {
        return null;
    }

    /**
     * Default to string function for in combo boxes.
     */
    @Override
    public String toString() {
        return uuid;
    }

    /** Uuid generator for the repository */
    public String generateUuid() {
        return UUID.randomUUID().toString();
    }

    /**
     * Gets the parent repository.
     *
     * @return The parent repository.
     */
    @JsonIgnore
    public R getParent() {
        return parent;
    }

    /**
     * Gets the uuid associated with this repository.
     *
     * @return The uuid.
     */
    public String uuid() {
        return uuid;
    }
    /** SET the uuid */
    public void uuid(String uuid) {
        this.uuid = uuid;
        if (!registered.containsKey(this.generatePath())) {
            registered.put(this.generatePath(), this);
        }
    }

    /**
     * Gets the file name for the saved file, will be saved in getDirName()/getFileName().
     * @return the saved file name or null if not set
     */
    public String getSavedFileName() {
        return null;
    }

    /**
     * Gets the dir segment before the file name
     * @return
     */
    public String getDirName() {
        return null;
    }

    /**
     * Retrieves the Plugin instance associated with this repository.
     *
     * @return The Plugin instance, or null if not found.
     */
    @JsonIgnore
    public Plugin getPlugin() {
        if (plugin != null) return plugin;

        Repository<?> p = this;
        Plugin plugin = null;

        // Traverse the parent hierarchy to find the first non-null Plugin
        while (p != null && p.getParent() != p) {
            plugin = p.getPlugin();
            if (plugin != null) {
                return p.getPlugin();
            }
            p = p.getParent();
        }

        return plugin; // Will be null if no Plugin is found
    }

    /** Initializes the repository */
    @SuppressWarnings("unchecked")
    public <T extends Repository<R>> T initialize() {
        try {
            load();
            if (!isInitialized) {
                fillData();
                isInitialized(true);
            }
        } catch (PlayerNotLoggedInException e) {
            System.out.println("Could not initialize " + this.getClass().getName() + "... Player not logged in.");
        } catch (RepositoryFileCorruptedException e) {
            fillData();
            isInitialized(true);
        } catch (IOException e) {
            if (!isInitialized) {
                fillData();
                isInitialized(true);
            }
        }
        return (T) Repository.registered.get(generatePath());
    }

    /** Imports a repository by json file */
    public static<T extends Repository<?>> T upload(File jsonFile, Class<T> clazz) throws IOException, MismatchedInputException {
        Deserializers.includeSerializationIgnoreFields(true);
        Deserializers.justStartedUpload(true);
        Deserializers.Repository.currentClass(clazz);
        Deserializers.Repository.currentNode(Json.objectMapper.readTree(jsonFile));
        Json.objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        Repository<?> repository = Json.objectMapper.readValue(jsonFile, clazz);
        repository.isInitialized(true);
        Deserializers.justStartedUpload(false);
        Deserializers.includeSerializationIgnoreFields(false);
        return (T) repository;
    }

    /** Imports a repository by json */
    public static<T extends Repository<?>> T upload(String serialized, Class<T> clazz) throws JsonProcessingException {
        Deserializers.includeSerializationIgnoreFields(true);
        Deserializers.justStartedUpload(true);
        Deserializers.Repository.currentClass(clazz);
        Deserializers.Repository.currentNode(Json.objectMapper.readTree(serialized));
        Json.objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        Repository<?> repository = Json.objectMapper.readValue(serialized, clazz);
        repository.isInitialized(true);
        Deserializers.justStartedUpload(false);
        Deserializers.includeSerializationIgnoreFields(false);
        return (T) repository;
    }

    /** Duplicates a repository (copy with new instance) */
    public<R extends Repository<?>> R duplicate() {
        return duplicate(null);
    }

    /** Duplicates a repository (copy with new instance) and assigns a uuid */
    @SuppressWarnings("unchecked")
    public<R extends Repository<?>> R duplicate(String uuid) {
        try {
            R repository = null;
            try {
                repository = (R) Deserializers.Repository.createRepositoryFromFileName(getClass(), uuid == null ? UUID.randomUUID().toString() : uuid);
            } catch (Exception ex) {
                System.out.println("Coulnd't instantiate repository while duplicating.");
                ex.printStackTrace();
            }
            if (repository == null) return null;
            repository.isInitialized(true);
            Injects.setInjectable((Class<R>)getClass(), repository);
            Class<?> clazz = this.getClass();
            while (clazz != null && clazz != clazz.getSuperclass()) {
                for (Field field : clazz.getDeclaredFields()) {
                    field.setAccessible(true);
                    if (field.getName().equals("uuid")) {
                        continue;
                    }
                    if (field.isAnnotationPresent(JsonIgnore.class)) {
                        continue;
                    }
                    else if (field.isAnnotationPresent(OneToOne.class)) {
                        Repository<?> oneToOne = (Repository<?>) field.get(this);
                        field.set(repository, oneToOne.duplicate());
                    }
                    else if (field.isAnnotationPresent(ManyToOne.class)) {
                        Repository.Property.List<Repository<?>> thisList = (Property.List<Repository<?>>) field.get(this);
                        Repository.Property.List<Repository<?>> otherList = (Property.List<Repository<?>>) field.get(repository);
                        for (Repository<?> r : thisList.values()) {
                            otherList.add(r.duplicate(r.uuid()));
                        }
                    }
                    else if (Repository.Property.List.class.isAssignableFrom(field.getType())) {
                        Repository.Property.List<Object> thisList = (Property.List<Object>) field.get(this);
                        Repository.Property.List<Object> otherList = (Property.List<Object>) field.get(repository);
                        for (Object r : thisList.values()) {
                            otherList.add(r);
                        }
                    }
                    else if (Repository.Property.Map.class.isAssignableFrom(field.getType())) {
                        Repository.Property.Map<Object, Object> thisMap = (Property.Map<Object, Object>) field.get(this);
                        Repository.Property.Map<Object, Object> otherMap = (Property.Map<Object, Object>) field.get(repository);
                        Type gType = field.getGenericType();
                        if (gType instanceof ParameterizedType) {
                            ParameterizedType param = (ParameterizedType) gType;
                            Type keyType = param.getActualTypeArguments()[0];
                            Type valueType = param.getActualTypeArguments()[1];
                            if (keyType instanceof Class && valueType instanceof Class) {
                                for (Object r : thisMap.keySet()) {
                                    String jsonKey = Json.objectMapper.writeValueAsString(r);
                                    String jsonValue = Json.objectMapper.writeValueAsString(thisMap.get(r));
                                    otherMap.set(
                                        Json.objectMapper.readValue(jsonKey, (Class<?>) keyType),
                                        Json.objectMapper.readValue(jsonValue, (Class<?>) valueType)
                                    );
                                }
                            }
                        }
                    }
                    else if (Repository.Property.class.isAssignableFrom(field.getType())) {
                        Repository.Property<Object> thisProperty = (Property<Object>) field.get(this);
                        Repository.Property<Object> otherProperty = (Property<Object>) field.get(repository);
                        Type gType = field.getGenericType();
                        if (gType instanceof ParameterizedType) {
                            ParameterizedType param = (ParameterizedType) gType;
                            Type type = param.getActualTypeArguments()[0];
                            otherProperty.replace(Json.objectMapper.readValue(Json.objectMapper.writeValueAsString(thisProperty.get()), (Class<?>) type));
                        }
                    }
                    else {
                        field.set(repository, Json.objectMapper.readValue(Json.objectMapper.writeValueAsString(field.get(this)), field.getType()));
                    }
                }
                clazz = clazz.getSuperclass();
            }

            // Reset
            Injects.setInjectable((Class<Repository<?>>)getClass(), this);

            return repository;
        } catch (Exception ignored) {
            // Reset
            Injects.setInjectable((Class<Repository<?>>)getClass(), this);

            ignored.printStackTrace();
        }
        return null;
    }

    /** Exports a repository to json */
    public String download() throws JsonProcessingException {
        Deserializers.includeSerializationIgnoreFields(true);
        try {
            String download = Json.objectMapper.writeValueAsString(this.duplicate());
            Deserializers.includeSerializationIgnoreFields(false);
            return download;
        }
        catch (Exception ex) {
            Deserializers.includeSerializationIgnoreFields(false);
            throw ex;
        }
    }

    /** Override this to fill the data upon initialization */
    public void fillData() {}

    /**
     * Constructs a new Repository with the specified parent and file name.
     *
     * @param parent The parent repository.
     * @param uuid The file name associated with this repository.
     */
    @SuppressWarnings("unchecked")
    public Repository(R parent, String uuid) {
        this.parent = parent;
        this.uuid = uuid;
        this.plugin = parent.getPlugin();
        if (!registered.containsKey(this.generatePath())) {
            registered.put(this.generatePath(), this);
        }
        Injects.setInjectable((Class<Repository<?>>)getClass(), registered.get(this.generatePath()));
    }

    /**
     * Constructs a new Repository with the specified Plugin and uuid.
     *
     * @param plugin The associated Plugin.
     * @param uuid The uuid for the repository.
     */
    @SuppressWarnings("unchecked")
    public Repository(Plugin plugin, String uuid) {
        this.parent = null; // No parent in this case
        this.uuid = uuid;
        this.plugin = plugin;
        if (!registered.containsKey(this.generatePath())) {
            registered.put(this.generatePath(), this);
        }
        Injects.setInjectable((Class<Repository<?>>)getClass(), registered.get(this.generatePath()));
    }

    /**
     * Saves the current state of the repository to a JSON file in the cache directory.
     */
    @SuppressWarnings("unchecked")
    public void save() {
        String fileName = generatePath(); // Generate the path for the cache file

        for (Field field : getClass().getDeclaredFields()) {
            // Save all many to ones
            if (field.isAnnotationPresent(ManyToOne.class)) {
                if (Property.List.class.isAssignableFrom(field.getType())) {
                    try {
                        Property.List<Object> list = (Property.List<Object>)field.get(this);
                        for (int i = 0; i < list.size(); i ++) {
                            Object value = list.get(i);
                            if (value instanceof Repository) {
                                Repository<?> repo = ((Repository<?>) value);
                                if (!repo.isInitialized) {
                                    list.set(i, repo.initialize());
                                }
                                else {
                                    repo.save();
                                }
                            }
                        }
                    }
                    catch (Exception ex) {
                        System.out.println("Could not save one to one relation.");
                        ex.printStackTrace();
                    }
                }
            }
            // Save all one to ones
            else if (field.isAnnotationPresent(OneToOne.class)) {
                if (Repository.class.isAssignableFrom(field.getType())) {
                    try {
                        Repository<?> repository = (Repository<?>) field.get(this);
                        if (repository == null) continue;
                        if (!repository.isInitialized) {
                            field.set(this, repository.initialize());
                        }
                        else {
                            repository.save();
                        }
                    }
                    catch (Exception ex) {
                        System.out.println("Could not save one to one relation.");
                        ex.printStackTrace();
                    }
                }
            }
        }
        for (Method method : getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(OneToOne.class)) {
                try {
                    Repository<?> repository = (Repository<?>) method.invoke(this);
                    if (repository != null) {
                        if (!repository.isInitialized) {
                            repository.initialize();
                        }
                        else {
                            repository.save();
                        }
                    }
                } catch (Exception ex) {
                    System.out.println("Could not save one to one relation in method.");
                }
            }
        }

        if (lastModified != 0 && !isDirty() && new File(fileName + ".json").exists()) return;
        if (fileName == null) return; // Exit if the file name is invalid

        try {
            Json.objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);

            // Serialize the repository to JSON
            String jsonString = Json.objectMapper.writeValueAsString(this);

            // Write the JSON string to a file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(fileName + ".json")))) {
                writer.write(jsonString);
                lastModified((new File(fileName + ".json")).lastModified());
                isDirty(false);
                // Save in local cache
                Repository.registered.put(generatePath(), initialize());
            } catch (IOException e) {
                e.printStackTrace(); // Log any IO exceptions
            }
        }
        catch (JsonProcessingException e) {
            e.printStackTrace(); // Log any JSON processing exceptions
        }
    }

    /**
     * Loads the repository state from a JSON file in the cache directory.
     *
     * @throws PlayerNotLoggedInException When the player is not found.
     * @throws RepositoryFileCorruptedException When the cache file is corrupted.
     * @throws IOException When the cache file cannot be opened.
     */
    @SuppressWarnings("unchecked")
    public void load() throws PlayerNotLoggedInException, RepositoryFileCorruptedException, IOException {
        String fileName = generatePath(); // Get the file name to load

        if (fileName == null) {
            System.out.println("Could not load player, player is offline.");
            throw new PlayerNotLoggedInException("Could not load player, player is offline.");
        }

        // Check if the JSON file exists
        File file = new File(fileName + ".json");

        // Do nothing if the file hasn't changed
        if (lastModified == file.lastModified() && !isDirty()) return;

        // Set modification timestamp
        this.lastModified(file.lastModified());

        if (file.exists()) {
            try {
                Deserializers.Repository.currentNode(Json.objectMapper.readTree(new File(fileName + ".json")));
                Deserializers.Repository.currentClass(getClass());
                Repository<R> data = Deserializers.Repository.currentNode().traverse(Json.objectMapper).readValueAs(getClass());
                Json.objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
                if (data == null) return;
                uuid(data.uuid());
                isInitialized(data.isInitialized());
                System.out.println(getClass().getName() + " : " + (isInitialized ? "true" : "false"));
                for (Field field : data.getClass().getDeclaredFields()) {
                    field.setAccessible(true);
                    deserializeField(field, data, this);
                }
                isDirty(false);
                lastModified(file.lastModified());
            } catch (Exception e) {
                e.printStackTrace();
                throw new RepositoryFileCorruptedException(); // Throw if an error occurs during loading
            }
        } else {
            throw new IOException("No previous user input found."); // Log if no previous data exists
        }
    }

    /**
     * Deserializes a field based on a new value, into the old value.
     * @param field the field
     * @param objectNew the new object
     * @param objectCurrent the current object
     */
    @SuppressWarnings("unchecked")
    protected void deserializeField(Field field, Object objectNew, Object objectCurrent) {
        try {
            field.setAccessible(true);
            Object valueNew = field.get(objectNew);
            Object valueCurrent = field.get(objectCurrent);
            
            // Get the generic type args if any
            Type[] typeArgs = new Type[] {};
            Type genericType = field.getGenericType();
            if (genericType instanceof ParameterizedType) {
                typeArgs = ((ParameterizedType) genericType).getActualTypeArguments();
            }

            // Null value
            if (valueNew == null) {
                // Also null
                if (valueCurrent == null) {
                    return;
                }
                // Property
                else if (valueCurrent instanceof Property) {
                    Property<?> propertyCurrent = (Property<?>) valueCurrent;
                    propertyCurrent.replace(null);
                }
                // Set null
                else {
                    field.set(objectCurrent, null);
                }
            }
            // Lists
            else if (valueNew instanceof Property.List) {
                Property.List<Object> listCurrent = (Property.List<Object>) valueCurrent;
                Property.List<Object> listNew = (Property.List<Object>) valueNew;
                listCurrent.replace(listNew);
            }
            // Maps
            else if (valueNew instanceof Property.Map) {
                Property.Map<Object, Object> mapCurrent = (Property.Map<Object, Object>) valueCurrent;
                Property.Map<Object, Object> mapNew = (Property.Map<Object, Object>) valueNew;
                mapCurrent.replace(mapNew);
            }
            // Properties
            else if (valueNew instanceof Property) {
                Property<Object> propertyNew = (Property<Object>) valueNew;
                Property<Object> propertyCurrent = (Property<Object>) valueCurrent;

                if (typeArgs.length > 0 && typeArgs[0] instanceof Class<?>) {
                    Type genericTypeParameter = typeArgs[0];
                    propertyCurrent.replace(Json.objectMapper.convertValue(propertyNew.get(), (Class<?>)genericTypeParameter));
                }
                else {
                    throw new Exception("No generic type in property.");
                }
            }
            // Other
            else {
                field.set(objectCurrent, valueNew);
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates the path for the cache file based on the plugin and repository hierarchy.
     *
     * @return The generated path as a String, or null if the path cannot be generated.
     */
    public String generatePath() {
        boolean isDevelopment = Plugin.HOME_DIR.getAbsolutePath().startsWith("C:\\Users\\lijko\\.runelite");
        String accountHash = Long.toString(plugin.getClient().getAccountHash());

        // If the account hash is -1 and not in development mode, return null
        if (accountHash.equals("-1") && !isDevelopment) return null;

        // Use "development" as the account hash in development mode
        if (isDevelopment) accountHash = "development";

        String dirName = this.getDirName();
        String path = dirName == null || dirName.equals(uuid)
            ? ""
            : dirName; // Start with the current file name
        Repository<?> parent = this.getParent(); // Get the parent repository

        // Construct the full path by traversing up the parent hierarchy
        while (parent != null && parent != parent.getParent()) {
            if (parent.getSavedFileName() != null) {
                String parentDirName = parent.getDirName();
                String parentFileName = parent.uuid();
                String parentPath = "";
                if (parentDirName != null && !parentDirName.isEmpty()) {
                    parentPath = parentDirName;
                }
                if (parentFileName != null && !parentFileName.isEmpty()) {
                    if (parentPath.isEmpty()) {
                        parentPath = parentFileName;
                    }
                    else {
                        parentPath = parentPath + "/" + parentFileName;
                    }
                }
                if (!parentPath.isEmpty()) {
                    path = parentPath + "/" + path;
                }
            }
            else {
                String parentDirName = parent.getDirName();
                path = parentDirName == null
                    ? path
                    : parentDirName + "/" + path;
            }
            parent = parent.getParent();
        }

        if (getSavedFileName() != null) {
            if (path.isEmpty()) {
                path += uuid();
            }
            else {
                path += "/" + uuid();
            }

            // Create the parent directories.
            new File(CACHE_DIR, "/" + path).mkdirs();

            // Combine the path with the cache directory and account hash
            path = new File(CACHE_DIR, "/" + path + "/" + getSavedFileName()).getAbsolutePath();
        }
        else {
            // Create the parent directories.
            new File(CACHE_DIR, "/" + path).mkdirs();

            // Combine the path with the cache directory and account hash
            path = new File(CACHE_DIR, "/" + path + "/" + uuid).getAbsolutePath();
        }


        return path; // Return the complete generated path
    }

    /**
     * Helper function to get the Repository.class in the deserializer.
     * @return
     */
    public Class<?> getClazz() {
        return getClass();
    }

    /**
     * A generic class representing a property that can have a value and notify listeners of changes.
     *
     * @param <V> The type of the value stored in this Property.
     */
    @JsonSerialize(using = Serializers.Property.class)
    @JsonDeserialize(using = Deserializers.Property.class)
    public static class Property<V> {
        protected V value; // The current value of the property

        /** SET Flag that marks the property as dirty (having changed since last save) */
        public void isDirty(boolean isDirty) {
            this.isDirty = isDirty;
        }
        /** GET Flag that marks the property as dirty (having changed since last save) */
        public boolean isDirty() {
            return isDirty;
        }
        /** Flag that marks the property as dirty (having changed since last save) */
        protected boolean isDirty = false;

        /** GET Flag that marks the property as having been dirty during this render cycle. */
        public boolean wasDirty() {
            return wasDirty;
        }
        /** SET Flag that marks the property as having been dirty during this render cycle. */
        public void wasDirty(boolean wasDirty) {
            this.wasDirty = wasDirty;
        }
        /** Flag that marks the property as having been dirty during this render cycle. */
        protected boolean wasDirty = false;

        // A list of listeners that are notified when the property's value changes
        @JsonIgnore
        private final java.util.List<Listener<V>> listeners = new ArrayList<>();

        /** Removes a listener */
        public void removeListener(Object listener) {
            for (int i = 0; i < listeners.size(); i ++) {
                if (listeners.get(i) == listener) {
                    listeners.remove(i);
                    break;
                }
            }
        }

        /**
         * Constructs a new Property with the specified initial value.
         *
         * @param value The initial value of the property.
         */
        public Property(V value) {
            this.value = value; // Set the initial value
        }

        /**
         * Gets the current value of the property.
         *
         * @return The current value.
         */
        @JsonIgnore
        public V get() {
            return value; // Return the current value
        }

        /** Get the current value of the property, or resorts to a default value */
        @JsonIgnore
        public V getOrDefault(V defaultValue) {
            return value == null ? defaultValue : value;
        }

        /** Checks if the value is null */
        public boolean isNull() {
            return this.value == null;
        }

        /** Checks whether the value is equal to another value, also contains null checks */
        public boolean isEqualTo(V value) {
            return (value == null && this.value == null)
                || (value != null && this.value != null && (this.value.equals(value) || this.value == value));
        }

        /**
         * Sets a new value for the property and notifies all registered listeners.
         *
         * @param value The new value to set.
         */
        @SuppressWarnings("unchecked")
        @JsonIgnore
        public void replace(V value) {
            if (value == null) {
                V oldValue = this.value;
                this.value = null;
                for (Listener<V> listener : listeners) {
                    listener.onSet(oldValue, value);
                }
            }
            else if (value instanceof Property.Map) {
                ((Property.Map<Object, Object>)this.value).replace((Property.Map<Object, Object>)value);
            }
            else if (value instanceof Property.List) {
                ((Property.List<Object>)this.value).replace((Property.List<Object>)value);
            }
            else if (value instanceof Property) {
                ((Property<Object>)this.value).replace((Property<Object>)value);
            }
            else {
                V oldValue = this.value; // Store the old value
                this.value = value; // Update the property with the new value
                // Notify all listeners of the value change
                for (Listener<V> listener : listeners) {
                    listener.onSet(oldValue, value); // Call onSet method of each listener
                }
            }
            isDirty = true;
        }

        /**
         * Registers a listener to be notified when the property's value changes.
         *
         * @param listener The listener to register.
         * @return This Property instance for method chaining.
         */
        public Property<V> listen(Listener<V> listener) {
            this.listeners.add(listener); // Add the listener to the list
            return this; // Return this instance for method chaining
        }

        /**
         * Notifies all listeners that the property is being deleted.
         */
        public void onDelete() {
            // Notify all listeners of the deletion event
            for (Listener<V> listener : listeners) {
                listener.onDelete(); // Call onDelete method of each listener
            }
        }

        @JsonIgnore
        public Class<?> getClazz() {
            return getClass();
        }

        /**
         * A generic class representing a list that extends the Property class.
         * This class can hold a list of values and notify listeners of changes to the list.
         *
         * @param <V> The type of the values contained in this list.
         */
        @JsonDeserialize(using = Deserializers.List.class)
        @JsonSerialize(using = Serializers.Property.class)
        public static class List<V> extends Property<java.util.List<Property<V>>> {
            // A list of listeners that are notified when changes occur to the list
            @JsonIgnore
            private final java.util.List<Listener<V>> listeners = new ArrayList<>();

            /**
             * Registers a listener to be notified when the list changes.
             *
             * @param listener The listener to register.
             * @return This List instance for method chaining.
             */
            public List<V> listen(Listener<V> listener) {
                this.listeners.add(listener); // Add the listener to the list
                return this; // Return this instance for method chaining
            }

            @SuppressWarnings("unchecked")
            public void replace(List<V> with) {
                java.util.List<Property<V>> currentValue = this.value;
                java.util.List<Property<V>> newValue = with.value;

                for (int i = Math.max(currentValue.size(), newValue.size()) - 1; i >= 0; i --) {
                    Property<V> currentEntry = i < currentValue.size() ? currentValue.get(i) : null;
                    Property<V> newEntry = i < newValue.size() ? newValue.get(i) : null;

                    if (currentEntry != null) {
                        if (newEntry != null) {
                            // On replace
                            if (currentEntry instanceof Property.Map && newEntry instanceof Property.Map) {
                                ((Property.Map<Object, Object>)currentEntry).replace((Property.Map<Object, Object>)newEntry);
                            }
                            else if (currentEntry instanceof Property.List && newEntry instanceof Property.List) {
                                ((Property.List<Object>)currentEntry).replace((Property.List<Object>)newEntry);
                            }
                            else if (currentEntry instanceof Property && newEntry instanceof Property) {
                                ((Property<Object>)currentEntry).replace(((Property<Object>)newEntry).get());   
                            }
                            for (Listener<V> listener : listeners) {
                                listener.onChange(currentEntry, i, i, currentEntry.get(), newEntry.get());
                            }
                        }
                        else {
                            // On delete
                            this.remove(i);
                        }
                    }
                    else if (newEntry != null) {
                        // On add
                        this.add(newEntry.get());
                    }
                }
                isDirty = true;
            }

            /**
             * Adds one or more values to the list and notifies listeners.
             *
             * @param values The values to add.
             * @return This List instance for method chaining.
             */
            @SafeVarargs
            public final List<V> add(V ...values) {
                java.util.List<Runnable> onDone = new ArrayList<>(); // Actions to perform after adding values
                for (V value : values) {
                    int index = this.value.size(); // Index at which to add the value
                    this.value.add(new Property<>(value)); // Add the value
                    // Notify listeners of the addition
                    for (Listener<V> listener : listeners) {
                        int i = index;
                        onDone.add(() -> {
                            listener.onAdd(this.value.get(i), i, value); // Notify listener of addition
                        });
                    }
                }
                // Execute deferred actions
                for (Runnable runnable : onDone) {
                    runnable.run();
                }
                isDirty = true;
                return this; // Return this instance for method chaining
            }

            /**
             * Removes an item at the specified index from the list and notifies listeners.
             *
             * @param indices The index of the item to remove.
             * @return This List instance for method chaining.
             */
            public List<V> remove(int ...indices) {
                java.util.List<V> toRemove = new ArrayList<>();
                for (int index : indices) {
                    toRemove.add(this.value.get(index).get());
                }
                for (V v : toRemove) {
                    this.remove(v);
                }
                isDirty = true;
                return this;
            }

            /**
             * Notifies listeners that an item was removed from the list.
             *
             * @param index The index of the removed item.
             * @param value The value of the removed item.
             */
            public void onRemove(Property<V> property, Integer index, V value) {
                for (Listener<V> listener : listeners) {
                    listener.onRemove(property, index, value); // Notify listener of removal
                }
            }

            /**
             * Notifies listeners that the list is being deleted.
             */
            public void onDelete() {
                for (Listener<V> listener : listeners) {
                    listener.onDelete(); // Notify listener of deletion
                }
            }

            /**
             * Checks if the list contains one or more specified values.
             *
             * @param values The values to check for.
             * @return True if the list contains the value(s), false otherwise.
             */
            public boolean contains(V ...values) {
                for (V value : values) {
                    boolean found = false;
                    for (Property<V> property : this.value) {
                        if (property.isEqualTo(value)) {
                            found = true;
                            break;
                        }
                    }
                    if (!found) return false;
                }
                return true;
            }

            /**
             * Removes one or more values from the list and notifies listeners.
             *
             * @param values The values to remove.
             * @return This List instance for method chaining.
             */
            @SafeVarargs
            public final List<V> remove(V... values) {
                java.util.List<Runnable> onDone = new ArrayList<>(); // Actions to perform after removal
                for (V value : values) {
                    for (int i = 0; i < this.value.size(); i++) {
                        Property<V> oldValue = this.value.get(i); // Get current value
                        // Check if the current value matches the value to remove
                        if (oldValue.get() == null || (oldValue.get() != value && !oldValue.get().equals(value))) continue; 
                        this.value.remove(i); // Remove the value
                        int index = i;
                        onDone.add(() -> {
                            this.onRemove(oldValue, index, oldValue.get()); // Notify removal
                        });
                    }
                }
                // Execute deferred actions
                for (Runnable runnable : onDone) {
                    runnable.run();
                }
                isDirty = true;
                return this; // Return this instance for method chaining
            }

            /**
             * Clears all values from the list and notifies listeners.
             *
             * @return This List instance for method chaining.
             */
            public List<V> clear() {
                this.value.clear(); // Clear the list
                for (Listener<V> listener : listeners) {
                    listener.onClear(); // Notify listeners that the list was cleared
                }
                isDirty = true;
                return this; // Return this instance for method chaining
            }

            /**
             * Sets a new value at the specified index and notifies listeners.
             *
             * @param index The index at which to set the new value.
             * @param value The new value to set.
             * @return This List instance for method chaining.
             */
            @JsonIgnore
            public List<V> set(int index, V value) {
                if (index < this.value.size()) {
                    V oldValue = this.value.get(index).get(); // Store the old value
                    this.value.get(index).replace(value);
                    // Notify listeners of the removal and addition
                    if (oldValue instanceof Property.List) {
                        ((Property.List<?>) oldValue).onDelete(); // Notify deletion
                    } else if (oldValue instanceof Property.Map) {
                        ((Property.Map<?, ?>) oldValue).onDelete(); // Notify deletion
                    } else if (oldValue instanceof Property) {
                        ((Property<?>) oldValue).onDelete(); // Notify deletion
                    }
                    for (Listener<V> listener : listeners) {
                        listener.onChange(this.value.get(index), index, index, oldValue, value);
                    }
                }
                isDirty = true;
                return this; // Return this instance for method chaining
            }

            /**
             * Inserts one or more values at the specified index and notifies listeners.
             *
             * @param index The index at which to insert the values.
             * @param values The values to insert.
             * @return This List instance for method chaining.
             */
            @SafeVarargs
            public final List<V> insert(int index, V... values) {
                java.util.List<Runnable> onDone = new ArrayList<>(); // Actions to perform after insertion
                if (this.value.size() >= index) {
                    for (int i = 0; i < values.length; i++) {
                        V value = values[i]; // Get value to insert
                        if (this.value.size() > index + i) {
                            V oldValue = this.value.get(index + i).get(); // Store old value at the index
                            this.value.add(index + i, new Property<>(value)); // Insert the new value
                            // Notify listeners of the change
                            for (Listener<V> listener : listeners) {
                                int ii = i;
                                onDone.add(() -> {
                                    listener.onChange(this.value.get(index + ii), index + ii, index + ii + 1, oldValue, value);
                                });
                            }
                        } else {
                            this.value.add(new Property<>(value)); // Add the new value at the end
                            for (Listener<V> listener : listeners) {
                                int ii = this.value.size() - 1;
                                onDone.add(() -> {
                                    listener.onAdd(this.value.get(ii), ii, this.value.get(ii).get()); // Notify listener of addition
                                });
                            }
                        }
                    }
                }
                // Execute deferred actions
                for (Runnable runnable : onDone) {
                    runnable.run();
                }
                isDirty = true;
                return this; // Return this instance for method chaining
            }

            /**
             * Retrieves the value at the specified index.
             *
             * @param index The index of the value to retrieve.
             * @return The value at the specified index, or null if the index is out of bounds.
             */
            @JsonIgnore
            public Property<V> getProperty(int index) {
                for (int i = 0; i < value.size(); i++) {
                    if (i == index) {
                        return value.get(i); // Return the value at the index
                    }
                }
                return null; // Return null if the index is out of bounds
            }

            /**
             * Retrieves the value at the specified index, or returns a default value if the index is out of bounds.
             *
             * @param index The index of the value to retrieve.
             * @param defaultValue The value to return if the index is out of bounds.
             * @return The value at the specified index, or the default value.
             */
            @JsonIgnore
            public Property<V> getProperty(int index, V defaultValue) {
                Property<V> get = this.getProperty(index); // Retrieve the value at the index
                return get == null ? new Property<>(defaultValue) : get; // Return default value if not found
            }

            /**
             * Retrieves the value at the specified index.
             *
             * @param index The index of the value to retrieve.
             * @return The value at the specified index, or null if the index is out of bounds.
             */
            @JsonIgnore
            public V get(int index) {
                Property<V> get = getProperty(index);
                return get == null ? null : get.get();
            }

            /**
             * Retrieves the value at the specified index, or returns a default value if the index is out of bounds.
             *
             * @param index The index of the value to retrieve.
             * @param defaultValue The value to return if the index is out of bounds.
             * @return The value at the specified index, or the default value.
             */
            @JsonIgnore
            public V get(int index, V defaultValue) {
                Property<V> get = this.getProperty(index); // Retrieve the value at the index
                return get == null ? defaultValue : get.get(); // Return default value if not found
            }

            /**
             * Get a java.util.List based on the value, mapped to the data type and not wrapped in a property.
             */
            public java.util.List<V> values() {
                java.util.List<V> list = new ArrayList<>();
                for (Property<V> property : this.value) {
                    if (property == null) {
                        list.add(null);
                    }
                    else {
                        list.add(property.get());
                    }
                }
                return list;
            }

            /**
             * Get the size of the list
             * @return
             */
            public int size() {
                return this.value.size();
            }

            /**
             * Gets a list with the datatype wrapped in its original properties.
             */
            public java.util.List<Property<V>> properties() {
                return value;
            }

            /**
             * Constructs a new List with an empty ArrayList as its initial value.
             */
            public List() {
                super(new ArrayList<Property<V>>());
            }

            /**
             * An abstract class representing a listener for changes to the List.
             *
             * @param <V> The type of values in the List.
             */
            public static abstract class Listener<V> {
                /**
                 * A property has been changed in the list
                 * @param property the property
                 * @param oldKey old index
                 * @param newKey new index
                 * @param oldValue old value
                 * @param newValue new value
                 */
                public void onChange(Property<V> property, Integer oldKey, Integer newKey, V oldValue, V newValue) {} // Notifies change

                /**
                 * When a property has been added to the list
                 * @param property the property
                 * @param key the index
                 * @param newValue the value
                 */
                public void onAdd(Property<V> property, int key, V newValue) {} // Notifies addition

                /**
                 * A property from the list was removed
                 * @param property the old property
                 * @param key the index
                 * @param oldValue the old value
                 */
                public void onRemove(Property<V> property, int key, V oldValue) {} // Notifies removal

                /**
                 * The list has been cleared
                 */
                public void onClear() {} // Notifies clearing of the list

                /**
                 * The entire list has been deleted
                 */
                public void onDelete() {} // Notifies deletion of the list
            }
        }

        /**
         * A map-like property that holds a collection of key-value pairs, where values are 
         * instances of {@link Property}. Supports listeners for detecting changes, additions, 
         * removals, and other operations on entries in the map.
         * 
         * @param <K> the type of keys maintained by this map
         * @param <V> the type of mapped values
         */
        @JsonSerialize(using = Serializers.Property.class)
        @JsonDeserialize(using = Deserializers.Map.class)
        public static class Map<K, V> extends Property<HashMap<K, Property<V>>> {
            /**
             * The event listeners
             */
            @JsonIgnore
            protected final java.util.List<Listener<K, V>> listeners = new ArrayList<>();

            /**
             * Registers a listener to this map. Listeners are notified of map events such as 
             * additions, removals, and replacements of values.
             * 
             * @param listener the listener to add to this map
             * @return this map instance for method chaining
             */
            public Map<K, V> listen(Listener<K, V> listener) {
                this.listeners.add(listener);
                return this;
            }

            @SuppressWarnings("unchecked")
            public void replace(Map<K, V> with) {
                java.util.Map<K, Property<V>> currentValue = this.value;
                java.util.Map<K, Property<V>> newValue = with.value;
                java.util.List<K> keys = new ArrayList<>();

                // Collect unique keys from both old and new values
                for (K key : currentValue.keySet()) {
                    if (!keys.contains(key)) keys.add(key);
                }
                for (K key : newValue.keySet()) {
                    if (!keys.contains(key)) keys.add(key);
                }

                // Loop through keys
                for (K key : keys) {
                    if (currentValue.containsKey(key)) {
                        if (newValue.containsKey(key)) {
                            // Replace
                            Property<V> currentProperty = currentValue.get(key);
                            Property<V> newProperty = newValue.get(key);
                            if (currentProperty instanceof Property.Map && newProperty instanceof Property.Map) {
                                ((Property.Map<Object, Object>)currentProperty).replace(((Property.Map<Object, Object>)newProperty).get());
                            }
                            else if (currentProperty instanceof Property.List && newProperty instanceof Property.List) {
                                ((Property.List<Object>)currentProperty).replace(((Property.List<Object>)newProperty).get());
                            }
                            else if (currentProperty instanceof Property && newProperty instanceof Property) {
                                ((Property<Object>)currentProperty).replace(((Property<Object>)newProperty).get());
                            } 
                        }
                        else {
                            // Remove
                            this.onRemove(key, currentValue.get(key));
                        }
                    }
                    else {
                        // Add
                        this.set(key, currentValue.get(key) == null ? null : currentValue.get(key).get());
                    }
                }
                isDirty = true;
            }

            /**
             * Removes specified properties from the map and notifies listeners of removals.
             * 
             * @param properties the keys to remove from the map
             * @return this map instance for method chaining
             */
            @SafeVarargs
            public final Map<K, V> remove(K... properties) {
                java.util.List<Runnable> onDone = new ArrayList<>();
                for (K property : properties) {
                    Property<V> value = this.value.get(property);
                    if (value == null) continue;

                    this.value.remove(property);

                    onDone.add(() -> onRemove(property, value));
                }
                for (int i = 0; i < onDone.size(); i++) {
                    onDone.get(i).run();
                }
                isDirty = true;
                return this;
            }

            /**
             * Invoked when a property is removed, notifying registered listeners of the removal.
             * 
             * @param property the removed property key
             * @param value the removed property value
             */
            public void onRemove(K property, Property<V> value) {
                for (Listener<K, V> listener : listeners) {
                    listener.onRemove(value, property, value.get());
                }
            }

            /**
             * Notifies listeners that the map has been deleted, triggering the `onDelete` 
             * method for each registered listener.
             */
            public void onDelete() {
                for (Listener<K, V> listener : listeners) {
                    listener.onDelete();
                }
            }

            /**
             * Checks if the map contains a specific key.
             * 
             * @param key the key whose presence is being tested
             * @return {@code true} if the map contains the specified key; {@code false} otherwise
             */
            public boolean containsKey(K key) {
                return this.value.containsKey(key);
            }

            /**
             * Checks if the map contains a specific value.
             * 
             * @param value the value whose presence is being tested
             * @return {@code true} if the map contains the specified value; {@code false} otherwise
             */
            public boolean containsValue(V value) {
                return this.value.containsValue(value);
            }

            /**
             * Clears all entries in the map and notifies listeners via the `onClear` event.
             * 
             * @return this map instance after clearing for method chaining
             */
            public Map<K, V> clear() {
                this.value.clear();
                for (Listener<K, V> listener : listeners) {
                    if (listener == null) continue;
                    listener.onClear();
                }
                isDirty = true;
                return this;
            }

            /**
             * Sets a new value for a specified key in the map. If the key already exists, the
             * old value is removed, and listeners are notified of the replacement. If the key
             * does not exist, a new entry is added, and listeners are notified of the addition.
             * 
             * @param property the key of the entry to add or update
             * @param newValue the new value to associate with the key
             * @return this map instance for method chaining
             */
            @JsonIgnore
            public Map<K, V> set(K property, V newValue) {
                boolean isNew = !this.value.containsKey(property);
                Property<V> oldProperty = this.value.get(property);
                V oldValue = oldProperty == null ? null : oldProperty.get();
                if (isNew) this.value.put(property, new Property<V>(newValue));
                else this.value.get(property).replace(newValue);
                java.util.List<Runnable> onDone = new ArrayList<>();

                if (oldProperty instanceof Property.List) {
                    ((Property.List<?>) oldProperty).onDelete();
                } else if (oldProperty instanceof Property.Map) {
                    ((Property.Map<?, ?>) oldProperty).onDelete();
                } else if (oldProperty instanceof Property) {
                    ((Property<?>) oldProperty).onDelete();
                }

                // Notify listeners of addition or replacement of an entry
                for (Listener<K, V> listener : listeners) {
                    if (listener == null) continue;
                    if (!isNew) {
                        listener.onChange(oldProperty, property, oldValue, newValue);
                    }
                    else {
                        listener.onAdd(oldProperty, property, newValue);
                    }
                }
                
                // Execute deferred actions
                for (int i = 0; i < onDone.size(); i++) {
                    onDone.get(i).run();
                }

                isDirty = true;

                return this;
            }

            /**
             * Retrieves the value associated with a specified key, or returns a provided 
             * default property if the key does not exist in the map.
             * 
             * @param property the key to look up in the map
             * @param defaultValue the default {@link Property} to return if the key is absent
             * @return the associated value if present, otherwise the default value
             */
            @JsonIgnore
            public V get(K property, V defaultValue) {
                Property<V> p = this.value.get(property);
                V v = p == null ? null : p.get();
                return v == null ? defaultValue : v;
            }

            /**
             * Retrieves the value associated with a specified key, or returns a provided 
             * default value if the key does not exist in the map.
             * 
             * @param property the key to look up in the map
             * @param defaultValue the default value to return if the key is absent
             * @return the associated value if present, otherwise the default value
             */
            @JsonIgnore
            public Property<V> getProperty(K property, Property<V> defaultValue) {
                Property<V> v = this.value.getOrDefault(property, defaultValue);
                return v == null ? defaultValue : v;
            }

            /**
             * Retrieves the value associated with a specified key.
             * 
             * @param property the key to look up in the map
             * @return the associated value if present, otherwise {@code null}
             */
            @JsonIgnore
            public Property<V> getProperty(K property) {
                return this.value.get(property);
            }

            /**
             * Get the key set of the map
             */
            public Set<K> keySet() {
                return this.value.keySet();
            }

            /**
             * Get all the values in the map
             */
            public java.util.List<V> values() {
                Iterator<Property<V>> iterator = this.value.values().iterator();
                java.util.List<V> values = new ArrayList<>();
                while (iterator.hasNext()) {
                    Property<V> next = iterator.next();
                    values.add(next.get());
                }
                return values;
            }

            /**
             * Gets the size of the map.
             */
            public int size() {
                return this.value.size();
            }

            /**
             * Retrieves the value associated with a specified key.
             *
             * @param property the key to look up in the map
             * @return the associated value if present, otherwise {@code null}
             */
            @JsonIgnore
            public V get(K property) {
                Property<V> value = this.value.get(property);
                if (value == null) return null;
                return value.get();
            }

            /**
             * Constructs an empty map with an internal {@link HashMap} to hold entries.
             */
            public Map() {
                super(new HashMap<>());
            }

            /**
             * Abstract listener class for handling various events in the map, such as 
             * additions, removals, updates, and deletions of entries.
             *
             * @param <K> the type of keys maintained by the map
             * @param <V> the type of mapped values
             */
            public static abstract class Listener<K, V> {
                /**
                 * Called when a new entry is added to the map.
                 *
                 * @param key the key of the new entry
                 * @param newValue the value of the new entry
                 */
                public void onAdd(Property<V> property, K key, V newValue) {}

                /**
                 * Called when an entry is removed from the map.
                 *
                 * @param key the key of the removed entry
                 * @param oldValue the value of the removed entry
                 */
                public void onChange(Property<V> property, K key, V oldValue, V newValue) {}

                /**
                 * Called when all entries in the map are cleared.
                 */
                public void onClear() {}

                /**
                 * Called when the map is replaced with a new map instance.
                 *
                 * @param oldMap the previous map instance
                 * @param newMap the new map instance
                 */
                public void onReplace(Map<K, V> oldMap, Map<K, V> newMap) {}

                /**
                 * Called when a key in the map is removed.
                 */
                public void onRemove(Property<V> property, K key, V oldValue) {}

                /**
                 * Called when the map itself is deleted, indicating a complete removal.
                 */
                public void onDelete() {}
            }
        }

        /**
         * Abstract listener class for handling property change events within a 
         * {@link Repository.Property} instance. This listener provides hooks for 
         * detecting updates and deletions of property values.
         *
         * @param <V> the type of the property value being listened to
         */
        public static abstract class Listener<V> {
            /**
             * Called when the property's value is updated or replaced.
             * This method is invoked when a new value is set in place of an 
             * existing value.
             *
             * @param oldValue the previous value of the property
             * @param newValue the new value of the property
             */
            public void onSet(V oldValue, V newValue) {}

            /**
             * Called when the property is deleted, indicating that the property
             * has been removed or otherwise made obsolete.
             */
            public void onDelete() {}
        }
    }
}
