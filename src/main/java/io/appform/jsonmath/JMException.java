package io.appform.jsonmath;

/**
 *
 */
public class JMException extends RuntimeException {
    public JMException(final String message) {
        this(message, null);
    }

    public JMException(final String message, Throwable t) {
        super(message, t);
    }
}
