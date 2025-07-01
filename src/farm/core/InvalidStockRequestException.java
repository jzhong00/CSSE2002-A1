package farm.core;

/**
 * Exception thrown when attempting to perform a stock operation with invalid parameters.
 */
public class InvalidStockRequestException extends Exception {
    /**
     * Constructs an InvalidStockRequestException with no message.
     */
    public InvalidStockRequestException() {
        super();
    }

    /**
     * Constructs an InvalidStockRequestException with the specified message
     * @param message The message provided.
     */
    public InvalidStockRequestException(String message) {
        super(message);
    }
}
