package io.hurx.models.repository.exceptions;

/**
 * Exception thrown when a player is not found in the repository.
 * <p>
 * This exception is typically used to indicate that an operation 
 * requiring a logged-in player has failed because the specified 
 * player could not be located. This situation often arises when 
 * the user is not logged in, preventing the repository from loading 
 * the player's data.
 * </p>
 */
public class PlayerNotLoggedInException extends Exception {

    /**
     * Constructs a new PlayerNotFoundException with the specified detail message.
     *
     * @param message the detail message that explains the reason for the exception.
     *                This message can be retrieved later by the {@link #getMessage()} method.
     */
    public PlayerNotLoggedInException(String message) {
        super(message);
    }

    /**
     * Constructs a new PlayerNotFoundException with no detail message.
     * This constructor can be used when no specific error message is available.
     */
    public PlayerNotLoggedInException() {
        super();
    }
}
