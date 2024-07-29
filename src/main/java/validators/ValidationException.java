package validators;

public class ValidationException extends Exception {

    /**
     * Constructs a ValidationException with a default message "Invalid Data."
     */
    public ValidationException() {
        super("Invalid Data.");
    }

    /**
     * Constructs a ValidationException with the specified detail message.
     *
     * @param message the detail message.
     */
    public ValidationException(String message) {
        super(message);
    }

    /**
     * Constructs a ValidationException with the specified cause.
     *
     * @param throwable the cause (which is saved for later retrieval by the getCause() method).
     */
    public ValidationException(Throwable throwable) {
        super(throwable);
    }

    /**
     * Constructs a ValidationException with the specified detail message and cause.
     *
     * @param message   the detail message.
     * @param throwable the cause (which is saved for later retrieval by the getCause() method).
     */
    public ValidationException(String message, Throwable throwable) {
        super(message, throwable);
    }


}
