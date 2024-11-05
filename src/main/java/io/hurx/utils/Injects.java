package io.hurx.utils;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.InjectableValues;

import io.hurx.models.repository.Repository;

public class Injects {
    public static final void reset() {
        injectables = new InjectableValues.Std();
        injectableValues.clear();
        Json.objectMapper.setInjectableValues(getInjectables());
    }

    public static final<C> void setInjectable(Class<C> c, C value) {
        getInjectables().addValue(c, value);
        injectableValues.put((Class<?>)c, (Object)value);
        Json.objectMapper.setInjectableValues(getInjectables());
    }

    public static final InjectableValues.Std getInjectables() {
        return injectables;
    }

    public static final Map<Class<?>, Object> getInjectableValues() {
        return injectableValues;
    }

    private static Map<Class<?>, Object> injectableValues = new HashMap<>();

    private static InjectableValues.Std injectables = new InjectableValues.Std();

    public static Map<String, Repository<?>> getRepositoriesByFileName() {
        return repositoriesByFileName;
    }
    private static Map<String, Repository<?>> repositoriesByFileName = new HashMap<>();
}
