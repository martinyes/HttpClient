package io.github.martinyes.httpclient.exception;

/**
 * Exception thrown when a redirect loop is detected.
 * <p>
 * @author martin
 */
public class RedirectLoopException extends Exception {

    /**
     * Constructs a new exception with {@code null} as its detail message. The cause is not initialized, and may be
     * initialized later.
     * @param message the detail message. The detail message is saved for later retrieval by the {@link #getMessage()}
     */
    public RedirectLoopException(String message) {
        super(message);
    }
}