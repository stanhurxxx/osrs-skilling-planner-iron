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

import io.hurx.models.repository.exceptions.FileCorruptedException;
import io.hurx.models.repository.exceptions.PlayerNotFoundException;
import io.hurx.plugin.Plugin;

public abstract class Repository<R extends Repository<?>> {
    /**
     * The directory of the cache
     */
    public final static File CACHE_DIR = new File(Plugin.HOME_DIR, "Ironman Skilling Planner");

    @JsonIgnore
    public R getParent() {
        return parent;
    }
    @JsonIgnore
    private R parent;
    
    public String getFileName() {
        return fileName;
    }
    protected String fileName;

    @JsonIgnore
    public Plugin getPlugin() {
        if (plugin != null) return plugin;

        Repository<?> p = this;
        Plugin plugin = null;
        while (p != null) {
            plugin = p.getPlugin();
            if (plugin != null) {
                return p.getPlugin();
            }
            p = p.getParent();
        }

        return plugin;
    }
    @JsonIgnore
    protected Plugin plugin;

    public Repository(R parent, String fileName) {
        this.parent = parent;
        this.fileName = fileName;
    }

    public Repository(Plugin plugin, String path) {
        this.parent = null;
        this.fileName = path;
        this.plugin = plugin;
    }

    /**
     * Saves the cache
     * @throws PlayerNotFoundException When the player is not found
     */
    public void save() {
        ObjectMapper objectMapper = new ObjectMapper();
        String fileName = generatePath();
        if (fileName == null) return;
        try {
            String jsonString = objectMapper.writeValueAsString(this);
            CACHE_DIR.mkdirs();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(CACHE_DIR, fileName + ".json")))) {
                writer.write(jsonString);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the cache
     * @throws PlayerNotFoundException When the player is not found
     * @throws FileCorruptedException When the cache is corrupted
     * @throws IOException When the cache couldn't be opened
     */
    public void load() throws PlayerNotFoundException, FileCorruptedException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String fileName = getFileName();
        if (fileName == null) {
            System.out.println("Could not load player, player is offline.");
            throw new PlayerNotFoundException("Could not load player, player is offline.");
        }

        if (new File(CACHE_DIR, fileName + ".json").exists()) {
            String input = "";
            try (BufferedReader reader = new BufferedReader(new FileReader(new File(CACHE_DIR, fileName + ".json")))) {
                input += reader.readLine() + "\n";
            } catch (IOException e) {
                e.printStackTrace();
                throw e;
            }
            try {
                Object data = objectMapper.readValue(input, getClass());

                // Loop through all the declared fields
                for (Field field : getClass().getDeclaredFields()) {
                    field.setAccessible(true);

                    Object newValue = field.get(data);
                    Object oldValue = field.get(this);

                    if (newValue instanceof Property && oldValue instanceof Property) {
                        Property<?> newProperty = (Property<?>) newValue;
                        Property<?> oldProperty = (Property<?>) oldValue;

                        Field value = Property.class.getDeclaredField("value");
                        if (value == null) throw new Exception("Someone removed the value property from the Repository.Property class.");

                        // Update the property, firing the listeners
                        value.set(oldProperty, newProperty.get());
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                throw new FileCorruptedException();
            }
        } else {
            System.out.println("No previous user input found.");
        }
    }

    public String generatePath() {
        boolean isDevelopment = Plugin.HOME_DIR.getAbsolutePath().startsWith("C:\\Users\\lijko\\.runelite")
            ? true
            : false;
        String accountHash = Long.toString(plugin.getClient().getAccountHash());
        
        if (accountHash == "-1" && !isDevelopment) return null;

        else if (isDevelopment) accountHash = "development";
        
        String path = this.fileName;
        Repository<?> parent = this.getParent();
        while (parent != null) {
            path = parent.getFileName() + "/" + path;
            parent = parent.getParent();
        }
        
        path = CACHE_DIR + "/" + accountHash + "/" + path;
        
        return path;
    }

    public static class Property<V> {
        @JsonIgnore
        public V get() {
            return value;
        }
        
        @JsonIgnore
        public void set(V value) {
            V oldValue = value;
            this.value = value;
            for (Listener<Integer, V> listener : listeners) {
                listener.onSet(null, value, oldValue);
            }
        }

        protected V value;

        protected final java.util.List<Listener<Integer, V>> listeners = new ArrayList<>();

        public Property(V value) {
            this.value = value;
        }

        public Property<V> listen(Listener<Integer, V> listener) {
            this.listeners.add(listener);
            return this;
        }

        public static class List<V> extends Property<java.util.List<V>> {
            protected final java.util.List<Listener<Integer, V>> listeners = new ArrayList<>();

            @SafeVarargs
            public final List<V> add(V ...values) {
                for (V value : values) {
                    this.value.add(value);
                    for (Listener<Integer, V> listener : listeners) {
                        listener.onAdd(this.value.size() - 2, value);
                    }
                }
                return this;
            }
            
            public List<V> remove(int index) {
                if (this.value.size() > index) {
                    V oldValue = this.value.get(index);
                    this.value.remove(index);
                    for (Listener<Integer, V> listener : listeners) {
                        listener.onRemove(index, oldValue);
                    }
                }
                return this;
            }

            public boolean contains(V value) {
                return this.value.contains(value);
            }

            @SafeVarargs
            public final List<V> remove(V ...values) {
                for (V value : values) {
                    for (int i = 0; i < this.value.size(); i ++) {
                        V oldValue = this.value.get(i);
                        if (oldValue != value && !oldValue.equals(value)) continue;
                        this.value.remove(i);
                        for (Listener<Integer, V> listener : listeners) {
                            listener.onRemove(i, oldValue);
                        }
                    }
                }
                return this;
            }

            public List<V> clear() {
                this.value.clear();
                for (Listener<Integer, V> listener : listeners) {
                    listener.onClear();
                }
                return this;
            }

            @JsonIgnore
            public List<V> set(int index, V value) {
                if (this.value.size() > index) {
                    V oldValue = this.value.get(index);
                    this.value.remove(index);
                    this.value.add(index, value);
                    for (Listener<Integer, V> listener : listeners) {
                        listener.onSet(null, value, oldValue);
                    }
                }
                return this;
            }

            @SafeVarargs
            public final List<V> insert(int index, V ...values) {
                if (this.value.size() >= index) {
                    for (int i = 0; i < values.length; i ++) {
                        V value = values[i];
                        if (this.value.size() > index + i) {
                            V oldValue = this.value.get(index + i);
                            this.value.add(index + i, value);
                            for (Listener<Integer, V> listener : listeners) {
                                listener.onChange(index + i, index + i + 1, oldValue, value);
                            }
                        }
                        else {
                            this.value.add(value);
                            for (Listener<Integer, V> listener : listeners) {
                                listener.onAdd(index + i, value);
                            }
                        }
                    }
                }
                return this;
            }

            @JsonIgnore
            public V get(int index) {
                for (int i = 0; i < value.size(); i ++) {
                    if (i == index) {
                        return value.get(i);
                    }
                }
                return null;
            }

            @JsonIgnore
            public V get(int index, V defaultValue) {
                V get = this.get(index);
                return get == null ? defaultValue : get;
            }

            public java.util.List<V> values() {
                return java.util.List.copyOf(value);
            }

            public List() {
                super(new ArrayList<>());
            }
        }

        public static class Map<K, V> extends Property<HashMap<K, Property<V>>> {    
            protected final java.util.List<Listener<K, V>> listeners = new ArrayList<>();

            @SafeVarargs
            public final Map<K, V> remove(K ...properties) {
                for (K property : properties) {
                    Property<V> value = this.value.get(property);
                    if (value == null) continue;
    
                    this.value.remove(property);
                    for (Listener<K, V> listener : listeners) {
                        if (listener == null) continue;
                        listener.onRemove(property, value.get());
                    }
                }
                return this;
            }

            public boolean containsKey(K key) {
                return this.value.containsKey(key);
            }

            public boolean containsValue(V value) {
                return this.value.containsValue(value);
            }

            public Map<K, V> clear() {
                this.value.clear();
                for (Listener<K, V> listener : listeners) {
                    if (listener == null) continue;
                    listener.onClear();
                }
                return this;
            }

            @JsonIgnore
            public Map<K, V> set(K property, V newValue) {
                boolean isNew = !this.value.containsKey(property);
                Property<V> oldValue = this.value.get(property);
                if (isNew) this.value.put(property, new Property<V>(newValue));
                for (Listener<K, V> listener : listeners) {
                    if (listener == null) continue;
                    listener.onSet(property, newValue, isNew ? null : oldValue.get());
                    if (isNew) listener.onAdd(property, newValue);
                }
                return this;
            }

            @JsonIgnore
            public V get(K property, Property<V> defaultValue) {
                Property<V> v = this.value.getOrDefault(property, defaultValue);
                this.value.put(property, v);
                return v == null ? null : v.get();
            }

            @JsonIgnore
            public V get(K property, V defaultValue) {
                Property<V> v = this.value.getOrDefault(property, new Property<V>(defaultValue));
                this.value.put(property, v);
                return v == null ? null : v.get();
            }

            @JsonIgnore
            public V get(K property) {
                Property<V> value = this.value.get(property);
                if (value == null) return null;
                return value.get();
            }

            public Map() {
                super(new HashMap<>());
            }
        }

        public static abstract class Listener<K, V> {
            public void onChange(K key, K newKey, V value, V newValue) {}
    
            public void onAdd(K key, V newValue) {}

            public void onRemove(K key, V oldValue) {}

            public void onClear() {}

            public void onSet(K key, V newValue, V oldValue) {}
        }
    }
}
