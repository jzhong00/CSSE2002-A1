package farm.core;

/**
 * Exception thrown when attempting to add a customer that already exists in the address book.
 */
public class DuplicateCustomerException extends Exception {
    /**
     * Constructs a DuplicateCustomerException with no message.
     */
    public DuplicateCustomerException() {
        super();
    }

    /**
     * Constructs a DuplicateCustomerException with the specified message.
     * @param message The provided message.
     */
    public DuplicateCustomerException(String message) {
        super(message);
    }
}
