package io.hurx.utils;

import io.hurx.models.repository.Repository;

public class Serializer {
    private Repository<?> repository;

    public Serializer(Repository<?> repository) {
        this.repository = repository;
    }

    public String serialize() {
        return "";
    }
}
