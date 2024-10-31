package io.hurx.models.repository.exceptions;

/**
 * Exception when a repository file got corrupted
 */
public class FileCorruptedException extends Exception {
    public FileCorruptedException(String message) {
        super(message);
    }   
    public FileCorruptedException() {
        super();
    }
}
