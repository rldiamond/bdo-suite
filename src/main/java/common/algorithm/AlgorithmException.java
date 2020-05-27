package common.algorithm;

/**
 * Exception to throw in algorithms.
 */
public class AlgorithmException extends Exception {

    public AlgorithmException() {
    }

    /**
     * Construct an exception with the provided message.
     *
     * @param message Message to use in the exception.
     */
    public AlgorithmException(String message) {
        super("Algorithm exception: " + message);
    }

    /**
     * Construct an exception with the provided message and cause exception.
     *
     * @param message Message to use in the exception.
     * @param cause   The originating exception.
     */
    public AlgorithmException(String message, Throwable cause) {
        super("Algorithm exception: " + message, cause);
    }
}
