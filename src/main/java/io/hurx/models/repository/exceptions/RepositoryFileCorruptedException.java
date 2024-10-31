package io.hurx.models.repository.exceptions;

/**
 * Exception thrown when a repository file (such as a JSON file) is corrupted.
 * <p>
 * This exception is used to indicate that the integrity of the repository file
 * cannot be guaranteed, typically due to malformed data or unexpected structure.
 * </p>
 */
public class RepositoryFileCorruptedException extends Exception {

    /**
     * Constructs a new FileCorruptedException with the specified detail message.
     *
     * @param message the detail message that explains the reason for the exception.
     *                This message can be retrieved later by the {@link #getMessage()} method.
     */
    public RepositoryFileCorruptedException(String message) {
        super(message);
    }

    /**
     * Constructs a new FileCorruptedException with no detail message.
     * This constructor can be used when no specific error message is available.
     */
    public RepositoryFileCorruptedException() {
        super();
    }
}
