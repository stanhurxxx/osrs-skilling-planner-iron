package io.hurx.models.repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.hurx.models.repository.exceptions.RepositoryFileCorruptedException;
import io.hurx.models.repository.exceptions.PlayerNotLoggedInException;
import io.hurx.plugin.Plugin;

/**
 * Abstract class representing a repository for managing cached data.
 *
 * @param <R> The type of the repository that extends this class.
 */
public abstract class Repository<R extends Repository<?>> {
    /**
     * The directory where the cache is stored.
     */
    public final static File CACHE_DIR = new File(Plugin.HOME_DIR, "Ironman Skilling Planner");

    @JsonIgnore
    private R parent; // Reference to the parent repository

    protected String fileName; // The name of the file associated with the repository

    @JsonIgnore
    protected Plugin plugin; // Reference to the associated Plugin instance

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

    /**
     * Constructs a new Repository with the specified parent and file name.
     *
     * @param parent The parent repository.
     * @param fileName The file name associated with this repository.
     */
    public Repository(R parent, String fileName) {
        this.parent = parent;
        this.fileName = fileName;
    }

    /**
     * Constructs a new Repository with the specified Plugin and path.
     *
     * @param plugin The associated Plugin.
     * @param path The path for the repository.
     */
    public Repository(Plugin plugin, String path) {
        this.parent = null; // No parent in this case
        this.fileName = path;
        this.plugin = plugin;
    }

    /**
     * Saves the current state of the repository to a JSON file in the cache directory.
     *
     * @throws PlayerNotLoggedInException When the player is not found.
     */
    public void save() {
        ObjectMapper objectMapper = new ObjectMapper();
        String fileName = generatePath(); // Generate the path for the cache file

        if (fileName == null) return; // Exit if the file name is invalid

        try {
            // Serialize the repository to JSON
            String jsonString = objectMapper.writeValueAsString(this);
            CACHE_DIR.mkdirs(); // Create the cache directory if it does not exist

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
    public void load() throws PlayerNotLoggedInException, RepositoryFileCorruptedException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String fileName = getFileName(); // Get the file name to load

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
                // Deserialize the JSON data into an object of this class
                Object data = objectMapper.readValue(input, getClass());

                // Loop through all declared fields to update properties
                for (Field field : getClass().getDeclaredFields()) {
                    field.setAccessible(true); // Make private fields accessible

                    Object newValue = field.get(data);
                    Object oldValue = field.get(this);

                    // If the fields are instances of Property, update them
                    if (newValue instanceof Property && oldValue instanceof Property) {
                        @SuppressWarnings("unchecked")
                        Property<Object> newProperty = (Property<Object>) newValue;
                        @SuppressWarnings("unchecked")
                        Property<Object> oldProperty = (Property<Object>) oldValue;

                        Field value = Property.class.getDeclaredField("value");
                        if (value == null) throw new Exception("Someone removed the value property from the Repository.Property class.");

                        // Update the property value, firing the listeners
                        oldProperty.set(newProperty.get());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RepositoryFileCorruptedException(); // Throw if an error occurs during loading
            }
        } else {
            System.out.println("No previous user input found."); // Log if no previous data exists
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

        String path = this.fileName; // Start with the current file name
        Repository<?> parent = this.getParent(); // Get the parent repository

        // Construct the full path by traversing up the parent hierarchy
        while (parent != null) {
            path = parent.getFileName() + "/" + path;
            parent = parent.getParent();
        }

        // Combine the path with the cache directory and account hash
        path = CACHE_DIR + "/" + accountHash + "/" + path;

        return path; // Return the complete generated path
    }

    /**
     * A generic class representing a property that can have a value and notify listeners of changes.
     *
     * @param <V> The type of the value stored in this Property.
     */
    public static class Property<V> {
        protected V value; // The current value of the property

        // A list of listeners that are notified when the property's value changes
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
        @JsonIgnore
        public void set(V value) {
            V oldValue = this.value; // Store the old value
            this.value = value; // Update the property with the new value

            // Notify all listeners of the value change
            for (Listener<V> listener : listeners) {
                listener.onSet(oldValue, value); // Call onSet method of each listener
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

        /**
         * A generic class representing a list that extends the Property class.
         * This class can hold a list of values and notify listeners of changes to the list.
         *
         * @param <V> The type of the values contained in this list.
         */
        public static class List<V> extends Property<java.util.List<V>> {
            // A list of listeners that are notified when changes occur to the list
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

            /**
             * Replaces the current list with another list and notifies listeners of changes.
             *
             * @param with The List to replace the current list with.
             * @throws IOException If there is an error during the replacement process.
             */
            public void replace(List<V> with) throws IOException {
                java.util.List<V> oldValue = this.value; // Store the old value
                java.util.List<V> newValue = with.value; // Get the new value from the provided list
                java.util.List<V> replacementValue = new ArrayList<>(); // Store values for the new list
                java.util.List<Runnable> onDone = new ArrayList<>(); // Actions to perform after replacement
                java.util.List<Runnable> onEnd = new ArrayList<>(); // Actions to perform at the end

                for (int i = 0; i < Math.max(oldValue.size(), newValue.size()); i++) {
                    V oldValueEntry = (i < oldValue.size()) ? oldValue.get(i) : null; // Get the old value entry
                    V newValueEntry = (i < newValue.size()) ? newValue.get(i) : null; // Get the new value entry

                    // Handle both old and new values
                    if (oldValueEntry != null && newValueEntry != null) {
                        ObjectMapper objectMapper = new ObjectMapper();

                        // Check if entries are lists and replace recursively
                        if (oldValueEntry instanceof Property.List && newValueEntry instanceof Property.List) {
                            @SuppressWarnings("unchecked")
                            Property.List<V> oldList = (Property.List<V>) oldValueEntry;
                            @SuppressWarnings("unchecked")
                            Property.List<V> newList = (Property.List<V>) newValueEntry;
                            oldList.replace(newList);
                        } 
                        // Check if entries are maps and replace recursively
                        else if (oldValueEntry instanceof Property.Map && newValueEntry instanceof Property.Map) {
                            @SuppressWarnings("unchecked")
                            Property.Map<Object, Object> oldMap = (Property.Map<Object, Object>) oldValueEntry;
                            @SuppressWarnings("unchecked")
                            Property.Map<Object, Object> newMap = (Property.Map<Object, Object>) newValueEntry;
                            oldMap.replace(newMap);
                        } 
                        // Handle generic Property objects
                        else if (oldValueEntry instanceof Property && newValueEntry instanceof Property) {
                            String serializedOld = objectMapper.writeValueAsString(oldValueEntry);
                            String serializedNew = objectMapper.writeValueAsString(newValueEntry);
                            // Skip replacement if they are equal
                            if (serializedOld.equals(serializedNew)) {
                                continue; // Skip further processing
                            }
                            int foundIndex = -1; // Track the found index for replacements
                            // Find the index of the new value in the list
                            for (int j = 0; j < with.value.size(); j++) {
                                String serializedNew2 = objectMapper.writeValueAsString(newValue.get(j));
                                if (serializedNew2.equals(serializedNew)) {
                                    foundIndex = j; // Found matching index
                                    int index = j;
                                    onDone.add(() -> {
                                        replacementValue.remove(index);
                                        replacementValue.add(oldValue.get(index)); // Restore the old value
                                    });
                                    break; // Exit the loop
                                }
                            }
                            // Notify listeners of changes
                            for (Listener<V> listener : this.listeners) {
                                if (foundIndex != -1) {
                                    int index = i;
                                    onEnd.add(() -> {
                                        listener.onChange(index, null, oldValueEntry, newValueEntry);
                                    });
                                } else {
                                    int index = i;
                                    onEnd.add(() -> {
                                        listener.onRemove(index, oldValueEntry);
                                    });
                                }
                            }
                            replacementValue.add(newValueEntry); // Add the new value
                        } else {
                            // Handle old values that are lists or maps and notify listeners
                            if (oldValueEntry instanceof Property.List) {
                                onEnd.add(() -> {
                                    @SuppressWarnings("unchecked")
                                    Property.List<Object> entry = (Property.List<Object>) oldValueEntry;
                                    entry.onRemove(null, null); // Notify removal
                                });
                            } else if (oldValueEntry instanceof Property.Map) {
                                onEnd.add(() -> {
                                    Property.Map<?, ?> entry = (Property.Map<?, ?>) oldValueEntry;
                                    entry.onDelete(); // Notify deletion
                                });
                            } else if (oldValueEntry instanceof Property) {
                                onEnd.add(() -> {
                                    Property<?> entry = (Property<?>) oldValueEntry;
                                    entry.onDelete(); // Notify deletion
                                });
                            }
                            replacementValue.add(newValue.get(i)); // Add new value
                        }
                    } else if (newValue.size() > i) {
                        // Handle case where only new value exists
                        replacementValue.add(newValue.get(i));
                    } else if (oldValue.size() > i) {
                        // Handle case where only old value exists
                        if (oldValueEntry instanceof Property.List) {
                            ((Property.List<?>) oldValueEntry).onDelete(); // Notify deletion
                        } else if (oldValueEntry instanceof Property.Map) {
                            ((Property.Map<?, ?>) oldValueEntry).onDelete(); // Notify deletion
                        } else if (oldValueEntry instanceof Property) {
                            ((Property<?>) oldValueEntry).onDelete(); // Notify deletion
                        }
                    }
                }

                // Notify all listeners that the list has been set
                for (Listener<V> listener : listeners) {
                    listener.onSet(this, with);
                }
                // Execute deferred actions
                for (Runnable runnable : onDone) {
                    runnable.run();
                }
                for (Runnable runnable : onEnd) {
                    runnable.run();
                }
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
                    this.value.add(value); // Add the value
                    // Notify listeners of the addition
                    for (Listener<V> listener : listeners) {
                        onDone.add(() -> {
                            listener.onAdd(index, value); // Notify listener of addition
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
             * @param index The index of the item to remove.
             * @return This List instance for method chaining.
             */
            @SuppressWarnings("unchecked")
            public List<V> remove(int index) {
                java.util.List<Runnable> onDone = new ArrayList<>(); // Actions to perform after removal
                if (this.value.size() > index) {
                    V value = this.value.get(index); // Get the value to remove
                    this.value.remove(index); // Remove the value
                    // Notify listeners based on the type of the removed value
                    if (value instanceof Property.List) {
                        ((Property.List<V>) value).onRemove(index, value); // Notify removal
                    } else if (value instanceof Property.Map) {
                        Property.Map<Object, Object> map = (Property.Map<Object, Object>) value;
                        map.onRemove((Object) index, (Property<Object>) value); // Notify removal
                    }
                }
                // Execute deferred actions
                for (Runnable runnable : onDone) {
                    runnable.run();
                }
                return this; // Return this instance for method chaining
            }

            /**
             * Notifies listeners that an item was removed from the list.
             *
             * @param index The index of the removed item.
             * @param value The value of the removed item.
             */
            public void onRemove(Integer index, V value) {
                for (Listener<V> listener : listeners) {
                    listener.onRemove(index, value); // Notify listener of removal
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
                        V oldValue = this.value.get(i); // Get current value
                        // Check if the current value matches the value to remove
                        if (oldValue != value && !oldValue.equals(value)) continue; 
                        this.value.remove(i); // Remove the value
                        int index = i;
                        onDone.add(() -> {
                            this.onRemove(index, oldValue); // Notify removal
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
                    V oldValue = this.value.get(index); // Store the old value
                    this.value.remove(index); // Remove the old value
                    this.value.add(index, value); // Add the new value
                    // Notify listeners of the removal and addition
                    if (oldValue instanceof Property.List) {
                        ((Property.List<?>) oldValue).onDelete(); // Notify deletion
                    } else if (oldValue instanceof Property.Map) {
                        ((Property.Map<?, ?>) oldValue).onDelete(); // Notify deletion
                    } else if (oldValue instanceof Property) {
                        ((Property<?>) oldValue).onDelete(); // Notify deletion
                    }
                    for (Listener<V> listener : listeners) {
                        listener.onRemove(index, oldValue); // Notify listener of removal
                        listener.onAdd(index, value); // Notify listener of addition
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
                            V oldValue = this.value.get(index + i); // Store old value at the index
                            this.value.add(index + i, value); // Insert the new value
                            // Notify listeners of the change
                            for (Listener<V> listener : listeners) {
                                int ii = i;
                                onDone.add(() -> {
                                    listener.onChange(index + ii, index + ii + 1, oldValue, value);
                                });
                            }
                        } else {
                            this.value.add(value); // Add the new value at the end
                            for (Listener<V> listener : listeners) {
                                int ii = i;
                                onDone.add(() -> {
                                    listener.onAdd(index + ii, value); // Notify listener of addition
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
            public V get(int index) {
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
            public V get(int index, V defaultValue) {
                V get = this.get(index); // Retrieve the value at the index
                return get == null ? defaultValue : get; // Return default value if not found
            }

            /**
             * Returns an unmodifiable view of the values contained in the list.
             *
             * @return An unmodifiable list of values.
             */
            public java.util.List<V> values() {
                return java.util.List.copyOf(value); // Return a copy of the list
            }

            /**
             * Constructs a new List with an empty ArrayList as its initial value.
             */
            public List() {
                super(new ArrayList<>()); // Initialize the property with an empty list
            }

            /**
             * An abstract class representing a listener for changes to the List.
             *
             * @param <V> The type of values in the List.
             */
            public static abstract class Listener<V> {
                public void onChange(Integer oldKey, Integer newKey, V oldValue, V newValue) {} // Notifies change
                
                public void onAdd(int key, V newValue) {} // Notifies addition
                
                public void onRemove(int key, V oldValue) {} // Notifies removal
                
                public void onClear() {} // Notifies clearing of the list
                
                public void onSet(List<V> oldList, List<V> newList) {} // Notifies setting of a new list
                
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

            /**
             * Replaces the current map values with those from the specified map. Listeners are 
             * notified of any changes to entries, including additions, removals, and updates.
             * 
             * @param with the map containing new values to replace the current map
             * @throws IOException if serialization fails when comparing values
             */
            public void replace(Map<K, V> with) throws IOException {
                java.util.Map<K, Property<V>> oldValue = this.value;
                java.util.Map<K, Property<V>> newValue = with.value;
                java.util.Map<K, Property<V>> replacementValue = new HashMap<>();
                java.util.List<Runnable> onDone = new ArrayList<>();
                java.util.List<Runnable> onEnd = new ArrayList<>();
                java.util.List<K> keys = new ArrayList<>();

                // Collect unique keys from both old and new values
                for (K key : oldValue.keySet()) {
                    if (!keys.contains(key)) keys.add(key);
                }
                for (K key : newValue.keySet()) {
                    if (!keys.contains(key)) keys.add(key);
                }

                for (K key : keys) {
                    // Obtain entries for each key in old and new maps
                    @SuppressWarnings("unchecked")
                    V oldValueEntry = (V) oldValue.get(key);
                    @SuppressWarnings("unchecked")
                    V newValueEntry = (V) newValue.get(key);

                    // Handle both old and new values present for the same key
                    if (oldValue.containsKey(key) && newValue.containsKey(key)) {
                        ObjectMapper objectMapper = new ObjectMapper();

                        // Handle List-type property updates
                        if (oldValueEntry instanceof Property.List && newValueEntry instanceof Property.List) {
                            @SuppressWarnings("unchecked")
                            Property.List<V> oldList = (Property.List<V>) oldValueEntry;
                            @SuppressWarnings("unchecked")
                            Property.List<V> newList = (Property.List<V>) newValueEntry;
                            oldList.replace(newList);
                        }
                        // Handle Map-type property updates
                        else if (oldValueEntry instanceof Property.Map && newValueEntry instanceof Property.Map) {
                            @SuppressWarnings("unchecked")
                            Property.Map<Object, Object> oldMap = (Property.Map<Object, Object>) oldValueEntry;
                            @SuppressWarnings("unchecked")
                            Property.Map<Object, Object> newMap = (Property.Map<Object, Object>) newValueEntry;
                            oldMap.replace(newMap);
                        }
                        // Handle generic Property updates with serialization checks
                        else if (oldValueEntry instanceof Property && newValueEntry instanceof Property) {
                            String serializedOld = objectMapper.writeValueAsString(oldValueEntry);
                            String serializedNew = objectMapper.writeValueAsString(newValueEntry);
                            if (serializedOld.equals(serializedNew)) continue;

                            K foundIndex = null;
                            for (K j : with.value.keySet()) {
                                String serializedNew2 = objectMapper.writeValueAsString(newValue.get(j));
                                if (serializedNew2.equals(serializedNew)) {
                                    foundIndex = j;
                                    K index = j;
                                    onDone.add(() -> {
                                        replacementValue.remove(index);
                                        replacementValue.put(index, oldValue.get(index));
                                    });
                                    break;
                                }
                            }

                            // Notify listeners of entry changes
                            for (Listener<K, V> listener : this.listeners) {
                                if (foundIndex != null) {
                                    K index = key;
                                    onEnd.add(() -> listener.onChange(index, null, oldValueEntry, newValueEntry));
                                } else {
                                    K index = key;
                                    onEnd.add(() -> listener.onRemove(index, oldValueEntry));
                                }
                            }
                            replacementValue.put(key, new Property<>(newValueEntry));
                        }
                    }
                    // Add new values to the map and notify listeners
                    else if (newValue.containsKey(key)) {
                        replacementValue.put(key, newValue.get(key));
                    }
                    // Remove old values from the map if not in new map
                    else if (oldValue.containsKey(key)) {
                        if (oldValueEntry instanceof Property.List) {
                            ((Property.List<?>) oldValueEntry).onDelete();
                        } else if (oldValueEntry instanceof Property.Map) {
                            ((Property.Map<?, ?>) oldValueEntry).onDelete();
                        } else if (oldValueEntry instanceof Property) {
                            ((Property<?>) oldValueEntry).onDelete();
                        }
                    }
                }

                // Notify listeners of the full map replacement
                for (Listener<K, V> listener : listeners) {
                    listener.onSet(this, with);
                }

                // Execute stored actions
                for (int i = 0; i < onDone.size(); i++) {
                    onDone.get(i).run();
                }
                for (int i = 0; i < onEnd.size(); i++) {
                    onEnd.get(i).run();
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
                    listener.onRemove(property, value.get());
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
                Property<V> oldValue = this.value.get(property);
                if (isNew) this.value.put(property, new Property<V>(newValue));
                java.util.List<Runnable> onDone = new ArrayList<>();

                if (oldValue instanceof Property.List) {
                    ((Property.List<?>) oldValue).onDelete();
                } else if (oldValue instanceof Property.Map) {
                    ((Property.Map<?, ?>) oldValue).onDelete();
                } else if (oldValue instanceof Property) {
                    ((Property<?>) oldValue).onDelete();
                }

                // Notify listeners of addition or replacement of an entry
                for (Listener<K, V> listener : listeners) {
                    if (listener == null) continue;
                    if (!isNew) listener.onRemove(property, oldValue.get());
                    listener.onAdd(property, newValue);
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
                 * Called when an entry in the map is updated with a new key or value.
                 *
                 * @param oldKey the previous key of the entry
                 * @param newKey the new key of the entry
                 * @param oldValue the previous value of the entry
                 * @param newValue the new value of the entry
                 */
                public void onChange(K oldKey, K newKey, V oldValue, V newValue) {}

                /**
                 * Called when a new entry is added to the map.
                 *
                 * @param key the key of the new entry
                 * @param newValue the value of the new entry
                 */
                public void onAdd(K key, V newValue) {}

                /**
                 * Called when an entry is removed from the map.
                 *
                 * @param key the key of the removed entry
                 * @param oldValue the value of the removed entry
                 */
                public void onRemove(K key, V oldValue) {}

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
                public void onSet(Map<K, V> oldMap, Map<K, V> newMap) {}

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
