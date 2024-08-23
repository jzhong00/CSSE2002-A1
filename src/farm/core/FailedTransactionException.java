package farm.core;

/**
 * An exception thrown to indicate that a transaction failed.
 */
public class FailedTransactionException extends Exception {
    /**
     * A constructor for the FailedTransactionException with no message.
     */
    public FailedTransactionException() {
        super();
    }

    /**
     * A constructor for the FailedTransactionException with the provided message.
     * @param message The specified message.
     */
    public FailedTransactionException(String message) {
        super(message);
    }
}
