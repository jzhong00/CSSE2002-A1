package farm.core;

/**
 * Exception thrown to indicate a customer could not be found in the address book.
 */
public class CustomerNotFoundException extends Exception {
    /**
     * Constructs a CustomerNotFoundException with no message.
     */
    public CustomerNotFoundException() {
        super();
    }

    /**
     * Constructs a CustomerNotFoundException with the detail message provided.
     * @param message The detail message.
     */
    public CustomerNotFoundException(String message) {
        super(message);
    }
}
