package io.hurx.cache.exceptions;

/**
 * Exception when the cache got corrupted
 */
public class CacheCorruptedException extends Exception {
    public CacheCorruptedException(String message) {
        super(message);
    }   
    public CacheCorruptedException() {
        super();
    }
}
