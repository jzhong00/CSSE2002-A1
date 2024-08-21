package farm.core;

public class FailedTransactionException extends Exception {
    public FailedTransactionException() {
        super();
    }
    public FailedTransactionException(String message) {
        super(message);
    }
}
