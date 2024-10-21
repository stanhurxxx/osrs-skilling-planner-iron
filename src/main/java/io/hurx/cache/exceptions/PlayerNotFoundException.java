package io.hurx.cache.exceptions;

/**
 * Exception when the player is not found.
 */
public class PlayerNotFoundException extends Exception {
    public PlayerNotFoundException(String message) {
        super(message);
    }
    public PlayerNotFoundException() {
        super();
    }
}