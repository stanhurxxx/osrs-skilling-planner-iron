package io.hurx.models.repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.type.TypeFactory;

import io.hurx.models.repository.exceptions.RepositoryFileCorruptedException;
import io.hurx.annotations.ManyToOne;
import io.hurx.models.IconPaths;
import io.hurx.models.repository.exceptions.PlayerNotLoggedInException;
import io.hurx.plugin.Plugin;
import io.hurx.utils.Injects;
import io.hurx.utils.Json;

/**
 * Abstract class representing a repository for managing cached data.
 *
 * @param <R> The type of the repository that extends this class.
 */
public abstract class Repository<R extends Repository<?>> {
    @JsonIgnore
    public <T extends Repository<?>> T get(Class<T> clazz, String fileName) {
        return null;
    }

    /**
     * The directory where the cache is stored.
     */
    @JsonIgnore
    public final static File CACHE_DIR = new File(Plugin.HOME_DIR.getAbsolutePath());

    @JsonIgnore
    private R parent; // Reference to the parent repository

    protected String fileName; // The name of the file associated with the repository

    @JsonIgnore
    protected Plugin plugin; // Reference to the associated Plugin instance

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
    public String toString() {
        return fileName;
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
     * Gets the file name associated with this repository.
     *
     * @return The file name.
     */
    public String getFileName() {
        return fileName;
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
        while (p != null) {
            plugin = p.getPlugin();
            if (plugin != null) {
                return p.getPlugin();
            }
            p = p.getParent();
        }

        return plugin; // Will be null if no Plugin is found
    }

    public abstract Repository<R> initialize();

    /**
     * Constructs a new Repository with the specified parent and file name.
     *
     * @param parent The parent repository.
     * @param fileName The file name associated with this repository.
     */
    public Repository(R parent, String fileName) {
        this();
        this.parent = parent;
        this.fileName = fileName;
        this.plugin = parent.getPlugin();
    }

    /**
     * Constructs a new Repository with the specified Plugin and path.
     *
     * @param plugin The associated Plugin.
     * @param path The path for the repository.
     */
    public Repository(Plugin plugin, String path) {
        this();
        this.parent = null; // No parent in this case
        this.fileName = path;
        this.plugin = plugin;
    }

    /**
     * Inititializes the (de)serializer for this repository into the
     * global objectMapper.
     */
    private Repository() {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(getClass(), new Deserializer<>());
        module.addSerializer(getClass(), new Serializer<>());
        Json.objectMapper.registerModule(module);
    }

    /**
     * Static function to create a repository based on a class and a filename. If it throws an exception, something is wrong with the code.
     * @param clazz the class
     * @param fileName the filename
     * @return the repository, or null if failed.
     */
    public static Repository<?> createRepositoryFromFileName(Class<?> clazz, String fileName) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Repository<?> instance = null;
        Constructor<?> constructor = null;
        if (fileName != null) {
            for (Constructor<?> c : clazz.getDeclaredConstructors()) {
                if (c.getParameters().length == 2 && Repository.class.isAssignableFrom(c.getParameters()[0].getType()) && c.getParameters()[1].getType() == String.class) {
                    // priority
                    constructor = c;
                }
            }
        }
        if (constructor == null) {
            for (Constructor<?> c : clazz.getDeclaredConstructors()) {
                if (c.getParameters().length == 1 && Repository.class.isAssignableFrom(c.getParameters()[0].getType())) {
                    constructor = c;
                }
                else if (c.getParameters().length == 1 && Plugin.class.isAssignableFrom(c.getParameters()[0].getType())) {
                    constructor = c;
                }
            }
            if (constructor == null) {
                return null;
            }
            else {
                instance = (Repository<?>)constructor.newInstance(Injects.getInjectableValues().get(constructor.getParameters()[0].getType()));
            }
        }
        else {
            instance = (Repository<?>)constructor.newInstance(Injects.getInjectableValues().get(constructor.getParameters()[0].getType()), fileName);
        }
        return (Repository<?>)instance;
    }

    /**
     * Saves the current state of the repository to a JSON file in the cache directory.
     *
     * @throws PlayerNotLoggedInException When the player is not found.
     */
    public void save() {
        String fileName = generatePath(); // Generate the path for the cache file

        if (fileName == null) return; // Exit if the file name is invalid

        try {
            // Serialize the repository to JSON
            ObjectMapper objectMapper = Json.objectMapper.copy();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            String jsonString = objectMapper.writeValueAsString(this);

            // Write the JSON string to a file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(fileName + ".json")))) {
                writer.write(jsonString);
            } catch (IOException e) {
                e.printStackTrace(); // Log any IO exceptions
            }
        } catch (JsonProcessingException e) {
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
    public Repository<R> load() throws PlayerNotLoggedInException, RepositoryFileCorruptedException, IOException {
        String fileName = generatePath(); // Get the file name to load

        if (fileName == null) {
            System.out.println("Could not load player, player is offline.");
            throw new PlayerNotLoggedInException("Could not load player, player is offline.");
        }

        // Check if the JSON file exists
        if (new File(fileName + ".json").exists()) {
            String input = "";
            // Read the contents of the file
            try (BufferedReader reader = new BufferedReader(new FileReader(new File(fileName + ".json")))) {
                input += reader.readLine() + "\n"; // Append the first line
            } catch (IOException e) {
                e.printStackTrace();
                throw e; // Rethrow any IO exceptions
            }
            try {
                @SuppressWarnings("unchecked")
                Repository<R> data = Json.objectMapper.readValue(input, getClass());
                if (data == null) return this;

                // Existing; Deserialize each field
                if (Injects.getRepositoriesByFileName().containsKey(data.getFileName())) {
                    @SuppressWarnings("unchecked")
                    Repository<R> existing = (Repository<R>)Injects.getRepositoriesByFileName().get(data.getFileName());
                    for (Field field : existing.getClass().getDeclaredFields()) {
                        deserializeField(field, data, this);
                    }
                    return existing;
                }
                // New; Add the new repository to the map
                else {
                    Injects.getRepositoriesByFileName().put(data.getFileName(), data);
                    return data;
                }
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
                field.set(objectNew, valueCurrent);
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
        String path = dirName == null
            ? ""
            : dirName + "/"; // Start with the current file name
        Repository<?> parent = this.getParent(); // Get the parent repository

        // Construct the full path by traversing up the parent hierarchy
        while (parent != null) {
            String parentDirName = parent.getDirName();
            path = parentDirName == null
                ? parent.getFileName() + "/" + path
                : parentDirName + "/" + parent.getFileName() + "/" + path;
            parent = parent.getParent();
        }

        // Create the parent directories.
        new File(CACHE_DIR, "/" + accountHash + "/" + path).mkdirs();

        // Combine the path with the cache directory and account hash
        path = new File(CACHE_DIR, "/" + accountHash + "/" + path + "/" + fileName).getAbsolutePath();

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
    @JsonSerialize(using = Property.Serializer.class)
    @JsonDeserialize(using = Property.Deserializer.class)
    public static class Property<V> {
        protected V value; // The current value of the property

        // A list of listeners that are notified when the property's value changes
        @JsonIgnore
        private final java.util.List<Listener<V>> listeners = new ArrayList<>();

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

        /**
         * Sets a new value for the property and notifies all registered listeners.
         *
         * @param value The new value to set.
         */
        @SuppressWarnings("unchecked")
        @JsonIgnore
        public void replace(V value) {
            if (value instanceof Property.Map) {
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
            }

            /**
             * Checks whether the list contains one or multiple values
             * @param values the values
             * @return true if it contains all values
             */
            public final boolean contains(@SuppressWarnings("unchecked") V... values) {
                for (V value : values) {
                    boolean found = false;
                    for (Property<V> property : this.value) {
                        if (property.get() == value || property.get().equals(value)) {
                            found = true;
                            break;
                        }
                    }
                    if (!found) return false;
                }
                return true;
            }

            /**
             * Adds one or more values to the list and notifies listeners.
             *
             * @param values The values to add.
             * @return This List instance for method chaining.
             */
            @SafeVarargs
            public final List<V> add(V... values) {
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
             * Checks if the list contains a specified value.
             *
             * @param value The value to check for.
             * @return True if the list contains the value, false otherwise.
             */
            public boolean contains(V value) {
                return this.value.contains(value); // Check if value is in the list
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
                return this; // Return this instance for method chaining
            }

            /**
             * Sets a new value at the specified index and notifies listeners.
             *
             * @param index The index at which to set the new value.
             * @param value The new value to set.
             * @return This List instance for method chaining.
             * @throws JsonProcessingException If there is an error during serialization.
             */
            @JsonIgnore
            public List<V> set(int index, V value) throws JsonProcessingException {
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
                return this; // Return this instance for method chaining
            }

            /**
             * Retrieves the value at the specified index.
             *
             * @param index The index of the value to retrieve.
             * @return The value at the specified index, or null if the index is out of bounds.
             */
            @JsonIgnore
            public Property<V> get(int index) {
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
            public Property<V> get(int index, V defaultValue) {
                Property<V> get = this.get(index); // Retrieve the value at the index
                return get == null ? new Property<>(defaultValue) : get; // Return default value if not found
            }

            public java.util.List<Property<V>> values() {
                return value;
            }

            /**
             * Constructs a new List with an empty ArrayList as its initial value.
             */
            @SuppressWarnings({ "rawtypes", "unchecked" })
            public List() {
                super(new ArrayList<Property<V>>()); // Initialize the property with an empty list
                SimpleModule module = new SimpleModule();
                module.addDeserializer((Class<Property.List<V>>)getClass(), new Deserializer());
                module.addSerializer((Class<Property.List<V>>)getClass(), new Serializer());
                Json.objectMapper.registerModule(module);
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
                        this.set(key, currentValue.get(key).get());
                    }
                }
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
            public V get(K property, Property<V> defaultValue) {
                Property<V> v = this.value.getOrDefault(property, defaultValue);
                this.value.put(property, v);
                return v == null ? null : v.get();
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
            public V get(K property, V defaultValue) {
                Property<V> v = this.value.getOrDefault(property, new Property<V>(defaultValue));
                this.value.put(property, v);
                return v == null ? null : v.get();
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
            @SuppressWarnings({ "unchecked", "rawtypes" })
            public Map() {
                super(new HashMap<K, Property<V>>());
                SimpleModule module = new SimpleModule();
                module.addDeserializer((Class<Property.Map<K, V>>)getClass(), new Deserializer());
                module.addSerializer((Class<Property.Map<K, V>>)getClass(), new Serializer());
                Json.objectMapper.registerModule(module);
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

        /**
         * The serializer for properties, lists and maps.
         * @param <V>
         */
        public static class Serializer<V> extends JsonSerializer<Property<V>> {
            @Override
            public void serialize(Property<V> value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                if (value != null) {
                    gen.writeObject(value.value);
                }
                else {
                    gen.writeNull();
                }
            }
        }

        /**
         * The deserializer for properties, lists and maps.
         * @param <V>
         */
        public static class Deserializer<V> extends JsonDeserializer<Property<V>> {
            @SuppressWarnings({ "rawtypes", "unchecked" })
            @Override
            public Property<V> deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
                // Read the JSON as a JsonNode
                // try
                JsonNode node = parser.getCodec().readTree(parser);
                Field field = Json.getCurrentField();
                if (field == null) {
                    throw new IOException("Current field is empty, so there's a Repository.Property being deserialized, before a Repository is being deserialized. They can only be used within repositories.");
                }

                if (field.getType() == Repository.Property.Map.class) {
                    Type gType = field.getGenericType();
                    if (gType instanceof ParameterizedType) {
                        ParameterizedType param = (ParameterizedType) gType;
                        Type keyType = param.getActualTypeArguments()[0];
                        Type valueType = param.getActualTypeArguments()[1];
                        Map<Object, Object> map = new Map<>();
                        ManyToOne manyToOne = field.isAnnotationPresent(ManyToOne.class)
                            ? field.getAnnotation(ManyToOne.class)
                            : null;
                        if (node.isArray() && manyToOne != null) {
                           for (JsonNode element : node) {
                                try {
                                    if (element.isObject()) {
                                        Repository<?> repository = Repository.createRepositoryFromFileName(manyToOne.type(), element.get("fileName").asText()).initialize();
                                        map.set(element.get("fileName").asText(), repository);
                                    }
                                    else if (element.isTextual()) {
                                        Repository<?> repository = Repository.createRepositoryFromFileName(manyToOne.type(), element.asText()).initialize();
                                        map.set(element.asText(), repository);
                                    }
                                } catch (Exception e) {
                                    System.out.println("Couldn't instantiate repository.");
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                        else if (node.isObject()) {
                            Iterator<java.util.Map.Entry<String, JsonNode>> iterator = node.fields();
                            while (iterator.hasNext()) {
                                java.util.Map.Entry<String, JsonNode> entry = iterator.next();
                                map.set(
                                    Json.objectMapper.readValue(entry.getKey(), TypeFactory.defaultInstance().constructType(keyType)),
                                    Json.objectMapper.readValue(entry.getValue().traverse(Json.objectMapper), TypeFactory.defaultInstance().constructType(valueType))
                                );
                            }
                        }
                        return (Property<V>)map;
                    }
                }
                else if (field.getType() == Repository.Property.List.class) {
                    Type gType = field.getGenericType();
                    if (gType instanceof ParameterizedType) {
                        ParameterizedType param = (ParameterizedType) gType;
                        Type type = param.getActualTypeArguments()[0];
                        List<Object> list = new List<>();
                        if (node.isArray()) {
                            ManyToOne manyToOne = field.isAnnotationPresent(ManyToOne.class)
                                ? field.getAnnotation(ManyToOne.class)
                                : null;
                            for (JsonNode element : node) {
                                if (manyToOne == null) {
                                    list.add(Json.objectMapper.readValue(element.traverse(Json.objectMapper), TypeFactory.defaultInstance().constructType(type)));
                                }
                                else {
                                    try {
                                        if (element.isObject()) {
                                            list.add(Repository.createRepositoryFromFileName(manyToOne.type(), element.get("fileName").asText()).initialize());
                                        }
                                        else if (element.isTextual()) {
                                            list.add(Repository.createRepositoryFromFileName(manyToOne.type(), element.asText()).initialize());
                                        }
                                    } catch (Exception e) {
                                        System.out.println("Couldn't instantiate repository.");
                                        throw new RuntimeException(e);
                                    }
                                }
                            }
                        }
                        return (Property<V>)list;
                    }
                }
                else if (field.getType() == Repository.Property.class) {
                    try {
                        Type gType = field.getGenericType();
                        if (gType instanceof ParameterizedType) {
                            ParameterizedType param = (ParameterizedType) gType;
                            Type type = param.getActualTypeArguments()[0];
                            Property<V> property = new Property(Json.objectMapper.readValue(node.traverse(Json.objectMapper), TypeFactory.defaultInstance().constructType(type)));
                            return property;
                        }
                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                        return null;
                    }
                }
                return null;
            }
        }
    }

    /**
     * The serializer for repositories.
     * @param <V>
     */
    public static class Serializer<V extends Repository<?>> extends JsonSerializer<V> {
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
                            if (field.isAnnotationPresent(JsonIgnore.class)) {
                                continue loop;
                            }
                            if (field.isAnnotationPresent(ManyToOne.class)) {
                                log = true;
                                field.setAccessible(true);
                                gen.writeFieldName(field.getName());
                                gen.writeStartArray();
                                Object fieldValue = field.get(value);
                                if (fieldValue instanceof Repository.Property.List) {
                                    for (Property<Repository<?>> v : ((Repository.Property.List<Repository<?>>) fieldValue).values()) {
                                        // gen.writeStartObject();
                                        gen.writeString(v.get().getFileName());
                                        // gen.writeEndObject();
                                    }
                                }
                                gen.writeEndArray();
                                continue loop;
                            } else {
                                try {
                                    // Get the value of the field from the object
                                    Object fieldValue = field.get(value);

                                    // Write the field name and its value to the JSON output
                                    gen.writeFieldName(field.getName());
                                    // Handle potential null values appropriately
                                    if (fieldValue == null) {
                                        gen.writeNull();
                                    } else {
                                        // Serialize the field value (uses the default serializer)
                                        gen.writeObject(fieldValue);
                                    }
                                } catch (IllegalAccessException e) {
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

    /**
     * The deserializer for repositories
     * @param <V>
     */
    public class Deserializer<V> extends JsonDeserializer<V> {        
        @SuppressWarnings("unchecked")
        @Override
        public V deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {          
            JsonNode node = p.getCodec().readTree(p);
            V instance = null;
            try {
                instance = (V)Repository.createRepositoryFromFileName(getClazz(), node.has("fileName") ? node.get("fileName").asText() : null);
            }
            catch (Exception ex) {
                throw new RuntimeException(ex);
            }

            if (instance == null) return (V)instance;
            if (node.isObject()) {
                Iterator<java.util.Map.Entry<String, JsonNode>> fields = node.fields();

                loop: while (fields.hasNext()) {
                    java.util.Map.Entry<String, JsonNode> entry = fields.next();
                    String name = entry.getKey();
                    JsonNode value = entry.getValue();
                    Class<?> nodeClazz = getClazz();
                    Class<?> previousNodeClazz = null;
                    while (nodeClazz != null && nodeClazz != previousNodeClazz) {
                        try {
                            // The declared field on the class
                            Field field = nodeClazz.getDeclaredField(name);
                            Json.setCurrentField(field);
                            // Ignored
                            if (field.isAnnotationPresent(JsonIgnore.class)) {
                                continue loop;
                            }
                            // Null values
                            if (value.isNull()) {
                                field.set(instance, null);
                                continue loop;
                            }
                            field.set(instance, Json.objectMapper.readValue(
                                    value.traverse(Json.objectMapper),
                                    field.getType()
                            ));
                            break;
                        }
                        catch (Exception ex) {
                            // Field doesnt exist on (sub)type.
                            previousNodeClazz = nodeClazz;
                            nodeClazz = getClazz().getSuperclass();
                        }
                    }
                }
            }
            return (V) instance;
        }
    }
}
