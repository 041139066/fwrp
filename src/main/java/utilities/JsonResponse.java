package utilities;

/**
 * The JsonResponse class represents a simple structure for a JSON response,
 * containing a status code and a message.
 */
public class JsonResponse {
    private int code;
    private String message;

    /**
     * Constructs a JsonResponse with the specified status code and message.
     *
     * @param code The status code of the response.
     * @param message The message associated with the response.
     */
    public JsonResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * Returns the status code of the response.
     *
     * @return The status code.
     */
    public int getCode() {
        return code;
    }

    /**
     * Sets the status code of the response.
     *
     * @param code The status code to set.
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * Returns the message associated with the response.
     *
     * @return The message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message associated with the response.
     *
     * @param message The message to set.
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
