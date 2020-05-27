package common.json;

/**
 * Custom exception to be used during JSON parsing.
 */
public class JsonParseException extends Exception {

    /**
     * Default constructor.
     */
    public JsonParseException() {
        super();
    }

    /**
     * Construct an exception with the provided message.
     *
     * @param message The message to include in the exception.
     */
    public JsonParseException(String message) {
        super("JsonParseException: " + message);
    }

    /**
     * Construct an exception with the provided message and source exception.
     *
     * @param message The message to include in the exception.
     * @param cause   The source exception causing this.
     */
    public JsonParseException(String message, Throwable cause) {
        super("JsonParseException: " + message, cause);
    }
}
